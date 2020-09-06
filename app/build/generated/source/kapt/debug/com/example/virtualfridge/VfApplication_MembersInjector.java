// Generated by Dagger (https://dagger.dev).
package com.example.virtualfridge;

import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class VfApplication_MembersInjector implements MembersInjector<VfApplication> {
  private final Provider<DispatchingAndroidInjector<Object>> dispatchingAndroidInjectorProvider;

  public VfApplication_MembersInjector(
      Provider<DispatchingAndroidInjector<Object>> dispatchingAndroidInjectorProvider) {
    this.dispatchingAndroidInjectorProvider = dispatchingAndroidInjectorProvider;
  }

  public static MembersInjector<VfApplication> create(
      Provider<DispatchingAndroidInjector<Object>> dispatchingAndroidInjectorProvider) {
    return new VfApplication_MembersInjector(dispatchingAndroidInjectorProvider);}

  @Override
  public void injectMembers(VfApplication instance) {
    injectDispatchingAndroidInjector(instance, dispatchingAndroidInjectorProvider.get());
  }

  @InjectedFieldSignature("com.example.virtualfridge.VfApplication.dispatchingAndroidInjector")
  public static void injectDispatchingAndroidInjector(VfApplication instance,
      DispatchingAndroidInjector<Object> dispatchingAndroidInjector) {
    instance.dispatchingAndroidInjector = dispatchingAndroidInjector;
  }
}
