package com.arkady.doordashlite.discover;

import com.arkady.doordashlite.data.DataManager;
import com.arkady.doordashlite.data.model.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DiscoverPresenterTest {
    private DiscoverContract.Presenter presenter;

    @Mock
    private DiscoverContract.View view;

    @Mock
    private DataManager dataManager;

    @Before
    public void setUp() {
        presenter = new DiscoverPresenter(dataManager, view);
        when(view.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter() {
        DiscoverContract.Presenter presenter = new DiscoverPresenter(dataManager, view);
        verify(view).setPresenter(presenter);
    }

    @Test
    public void loadRestaurants() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DataManager.LoadRestaurantsCallback)invocation.getArguments()[0]).onRestaurantsLoaded(new ArrayList<Restaurant>());
                return null;
            }
        }).when(dataManager).getRestaurants(any(DataManager.LoadRestaurantsCallback.class));

        presenter.loadRestaurants(true);
        verify(view).setLoadingIndicator(eq(true));
        verify(view).setLoadingIndicator(eq(false));
        verify(dataManager).getRestaurants(any(DataManager.LoadRestaurantsCallback.class));
    }

    @Test
    public void loadRestaurantsFailure() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DataManager.LoadRestaurantsCallback)invocation.getArguments()[0]).onDataNotAvailable();
                return null;
            }
        }).when(dataManager).getRestaurants(any(DataManager.LoadRestaurantsCallback.class));

        presenter.loadRestaurants(true);
        verify(view).setLoadingIndicator(eq(true));
        verify(view).setLoadingIndicator(eq(false));
        verify(view).showLoadingRestaurantsError();
        verify(dataManager).getRestaurants(any(DataManager.LoadRestaurantsCallback.class));
    }
}