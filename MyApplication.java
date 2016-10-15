public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // The Realm file will be located in Context.getFilesDir() with name "default.realm"
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder().build();
    Realm.setDefaultConfiguration(config);
  }
}