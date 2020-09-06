package com.example.virtualfridge;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lcom/example/virtualfridge/VfApplicationComponent;", "", "inject", "", "application", "Lcom/example/virtualfridge/VfApplication;", "Builder", "app_debug"})
@dagger.Component(modules = {dagger.android.AndroidInjectionModule.class, com.example.virtualfridge.data.api.ExampleApiModule.class, com.example.virtualfridge.ActivitiesModule.class})
@javax.inject.Singleton()
public abstract interface VfApplicationComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    com.example.virtualfridge.VfApplication application);
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\'J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/example/virtualfridge/VfApplicationComponent$Builder;", "", "applicationBind", "application", "Landroid/app/Application;", "build", "Lcom/example/virtualfridge/VfApplicationComponent;", "app_debug"})
    @dagger.Component.Builder()
    public static abstract interface Builder {
        
        @org.jetbrains.annotations.NotNull()
        public abstract com.example.virtualfridge.VfApplicationComponent build();
        
        @org.jetbrains.annotations.NotNull()
        @dagger.BindsInstance()
        public abstract com.example.virtualfridge.VfApplicationComponent.Builder applicationBind(@org.jetbrains.annotations.NotNull()
        android.app.Application application);
    }
}