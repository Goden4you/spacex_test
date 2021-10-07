package com.example.spacextest

import com.example.spacextest.mappers.SuccessedMissionsMapper
import com.example.spacextest.presentation.MissionsViewModel
import com.spacex.data.api.BASE_URL
import com.spacex.data.api.MissionsApi
import com.spacex.data.api.NetworkModule
import com.spacex.data.datasources.MissionsRemoteDataSourceImpl
import com.spacex.data.mappers.MissionApiResponseMapper
import com.spacex.data.repository.MissionsRepositoryImpl
import com.spacex.domain.usecases.GetMissionsUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RequestViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var viewModel: MissionsViewModel

    private var missionsRepository: MissionsRepositoryImpl? = null

    private val networkModule by lazy {
        NetworkModule()
    }

    private var apiHelper: MissionsApi? = null

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val newRepo =
            MissionsRepositoryImpl(
                MissionsRemoteDataSourceImpl(
                    networkModule.createMissionsApi(BASE_URL),
                    MissionApiResponseMapper()
                )
            )
        missionsRepository = newRepo

        viewModel = MissionsViewModel(
            GetMissionsUseCase(
                missionsRepository!!
            ),
            SuccessedMissionsMapper()
        )

        apiHelper = networkModule.createMissionsApi(BASE_URL)

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `fetch details and check response Code 200 returned`() = runBlocking<Unit> {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val  actualResponse = apiHelper?.getAllMissions()
        // Assert
        assertEquals(response.toString().contains("200"),actualResponse?.code().toString().contains("200"))
    }

    @Test
    fun `fetch details for failed response 400 returned`() = runBlocking<Unit> {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader("failed_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val  actualResponse = apiHelper?.getAllMissions()
        // Assert
        assertEquals(response.getBody().toString().contains("id"),!actualResponse?.body().toString().contains("id"))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}