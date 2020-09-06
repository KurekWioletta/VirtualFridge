// Generated by Dagger (https://dagger.dev).
package com.example.virtualfridge;

import android.app.Application;
import com.example.virtualfridge.data.api.ExampleApi;
import com.example.virtualfridge.data.api.ExampleApiModule_ProvideExampleApiFactory;
import com.example.virtualfridge.data.api.ExampleApiModule_ProvideOkHttpClientFactory;
import com.example.virtualfridge.data.api.ExampleApiModule_ProvideRetrofitFactory;
import com.example.virtualfridge.ui.main.MainActivity;
import com.example.virtualfridge.ui.main.MainActivityPresenter;
import com.example.virtualfridge.ui.main.MainActivity_MembersInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.internal.Preconditions;
import java.util.Collections;
import java.util.Map;
import javax.inject.Provider;
import retrofit2.Retrofit;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerVfApplicationComponent implements VfApplicationComponent {
  private Provider<ActivitiesModule_BindMainActivity.MainActivitySubcomponent.Factory> mainActivitySubcomponentFactoryProvider;

  private DaggerVfApplicationComponent(Application applicationBind) {

    initialize(applicationBind);
  }

  public static VfApplicationComponent.Builder builder() {
    return new Builder();
  }

  private Map<Class<?>, Provider<AndroidInjector.Factory<?>>> getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(
      ) {
    return Collections.<Class<?>, Provider<AndroidInjector.Factory<?>>>singletonMap(MainActivity.class, (Provider) mainActivitySubcomponentFactoryProvider);}

  private DispatchingAndroidInjector<Object> getDispatchingAndroidInjectorOfObject() {
    return DispatchingAndroidInjector_Factory.newInstance(getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(), Collections.<String, Provider<AndroidInjector.Factory<?>>>emptyMap());}

  private Retrofit getRetrofit() {
    return ExampleApiModule_ProvideRetrofitFactory.provideRetrofit(ExampleApiModule_ProvideOkHttpClientFactory.provideOkHttpClient());}

  private ExampleApi getExampleApi() {
    return ExampleApiModule_ProvideExampleApiFactory.provideExampleApi(getRetrofit());}

  @SuppressWarnings("unchecked")
  private void initialize(final Application applicationBind) {
    this.mainActivitySubcomponentFactoryProvider = new Provider<ActivitiesModule_BindMainActivity.MainActivitySubcomponent.Factory>() {
      @Override
      public ActivitiesModule_BindMainActivity.MainActivitySubcomponent.Factory get() {
        return new MainActivitySubcomponentFactory();}
    };
  }

  @Override
  public void inject(VfApplication application) {
    injectVfApplication(application);}

  private VfApplication injectVfApplication(VfApplication instance) {
    VfApplication_MembersInjector.injectDispatchingAndroidInjector(instance, getDispatchingAndroidInjectorOfObject());
    return instance;
  }

  private static final class Builder implements VfApplicationComponent.Builder {
    private Application applicationBind;

    @Override
    public Builder applicationBind(Application application) {
      this.applicationBind = Preconditions.checkNotNull(application);
      return this;
    }

    @Override
    public VfApplicationComponent build() {
      Preconditions.checkBuilderRequirement(applicationBind, Application.class);
      return new DaggerVfApplicationComponent(applicationBind);
    }
  }

  private final class MainActivitySubcomponentFactory implements ActivitiesModule_BindMainActivity.MainActivitySubcomponent.Factory {
    @Override
    public ActivitiesModule_BindMainActivity.MainActivitySubcomponent create(MainActivity arg0) {
      Preconditions.checkNotNull(arg0);
      return new MainActivitySubcomponentImpl(arg0);
    }
  }

  private final class MainActivitySubcomponentImpl implements ActivitiesModule_BindMainActivity.MainActivitySubcomponent {
    private final MainActivity arg0;

    private MainActivitySubcomponentImpl(MainActivity arg0Param) {
      this.arg0 = arg0Param;
    }

    private MainActivityPresenter getMainActivityPresenter() {
      return new MainActivityPresenter(arg0, DaggerVfApplicationComponent.this.getExampleApi());}

    @Override
    public void inject(MainActivity arg0) {
      injectMainActivity(arg0);}

    private MainActivity injectMainActivity(MainActivity instance) {
      MainActivity_MembersInjector.injectPresenter(instance, getMainActivityPresenter());
      return instance;
    }
  }
}
