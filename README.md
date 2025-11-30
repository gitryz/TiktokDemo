# TiktokDemo（Android/Java）

一个高仿抖音“经验”频道的 Demo。实现以下功能：  
  - 瀑布流布局：实现抖音经验页的双列瀑布流界面，支持动态高度适配
  - 经验卡片 UI 组件：包含图片、标题、用户头像、用户名、点赞数等核心元素
  - 点赞交互功能：支持点击点赞图标切换点赞状态，实时更新点赞计数
  - 数据刷新机制：实现上拉加载更多数据和下拉刷新最新内容的功能
  - 数据配置管理：支持 Mock 数据模拟，配置网络图片资源，确保数据可动态更新

- 应用 ID：`com.example.tiktokdemo`
- 语言：Java 11（`sourceCompatibility/targetCompatibility=11`）
- 构建工具：Gradle 8.13
- SDK：`compileSdk=36`，`targetSdk=36`，`minSdk=21`

---

## 功能概览

- 启动页（`SplashActivity`）：1 秒后自动进入主页。
- 主页（`MainActivity`）：顶部 `TabLayout`，默认选中“经验”Tab。
- 经验页（`ExperienceFragment`）：
  - `RecyclerView` + `StaggeredGridLayoutManager` 实现 2 列瀑布流。
  - 卡片包含主图、标题、圆形头像、用户名、点赞图标与点赞数。
  - 点赞交互：点击心形图标在“已赞/未赞”间切换，并同步增减点赞数。
  - 图片动态高度：按原始宽高比计算目标高度并设置最小/最大高度，保证瀑布流观感。
  - 支持“上滑加载更多”和“下拉刷新”的数据接口。
- 数据与加载：
  - 模型 `FeedItem` 同时支持本地资源与网络 URL，两套构造方法分别覆盖本地与网络模式。
  - `FeedDataGenerator` 提供本地/网络两种数据生成方法，包含随机昵称、标题、点赞数与资源/URL。

---

## 项目结构

- 根目录
  - `settings.gradle`、`build.gradle`、`gradle.properties`
  - `gradle/libs.versions.toml`（集中声明依赖与版本）
  - `gradle/wrapper/gradle-wrapper.properties`（Gradle 版本 8.13）
- App 模块（`app/`）
  - 构建配置：`app/build.gradle`
  - 清单：`app/src/main/AndroidManifest.xml`
  - 活动（Activities）：
    - `app/src/main/java/com/example/tiktokdemo/activity/SplashActivity.java`
    - `app/src/main/java/com/example/tiktokdemo/activity/MainActivity.java`
  - 碎片（Fragments）：
    - `app/src/main/java/com/example/tiktokdemo/fragment/ExperienceFragment.java`
    - `app/src/main/java/com/example/tiktokdemo/fragment/TodoFragment.java`
  - RecyclerView：
    - 适配器：`ui/recyclerview/adapter/StaggeredAdapter.java`
    - ViewHolder：`ui/recyclerview/viewholder/ImageViewHolder.java`
    - 数据模型：`ui/recyclerview/bean/FeedItem.java`
  - 数据生成器：`app/src/main/java/com/example/tiktokdemo/util/FeedDataGenerator.java`
  - 主要布局：
    - `res/layout/activity_splash.xml`
    - `res/layout/activity_main.xml`
    - `res/layout/fragment_experience.xml`
    - `res/layout/recycler_item_image.xml`（单个卡片布局）
  - 可绘制资源（示例）：`res/drawable/like_icon.xml`、`untap_like_icon.xml`、`circle_background.xml`

---

## 依赖与版本

- AndroidX
  - AppCompat: `androidx.appcompat:appcompat:1.7.1`
  - Activity: `androidx.activity:activity:1.11.0`
  - ConstraintLayout: `androidx.constraintlayout:constraintlayout:2.2.1`
  - Navigation: `androidx.navigation:navigation-fragment:2.6.0`、`androidx.navigation:navigation-ui:2.6.0`
  - SwipeRefreshLayout: `androidx.swiperefreshlayout:swiperefreshlayout:1.1.0`
- Material: `com.google.android.material:material:1.13.0`
- 图片加载：Glide `com.github.bumptech.glide:glide:4.16.0`

---

## 权限

- 清单权限（`AndroidManifest.xml`）：
  - `android.permission.INTERNET`
  - `android.permission.ACCESS_NETWORK_STATE`

---

## 数据模型与展示

- `FeedItem`：
  - 本地模式构造：`FeedItem(int imageResId, String title, int avatarResId, String username, int likeCount)`
  - 网络模式构造：`FeedItem(String imageUrl, String title, String avatarUrl, String username, int likeCount)`
  - 关键字段：主图、本地/网络头像、标题、用户名、点赞数、点赞状态、是否网络图片 `isNetworkImage()`。
- `StaggeredAdapter`：
  - 构造：`StaggeredAdapter(Context, List<FeedItem>)`
  - 提供 `addData(List<FeedItem>)`（上拉加载更多）与 `refreshData(List<FeedItem>)`（下拉刷新）。
  - 当滚动到倒数第 3 个条目时触发 `OnLoadMoreListener#onLoadMore()`。
- `ImageViewHolder#bindData(...)`：
  - 本地图片使用 `BitmapFactory.Options` 读取宽高；网络图片使用 Glide 并在 `RequestListener` 中拿到资源尺寸。
  - 按宽高比计算目标高度，并限定最小 260dp、最大 600dp 后设置到 ImageView。
  - 点赞按钮点击切换图标（`like_icon`/`untap_like_icon`）并更新数值。

---

## 构建与运行

使用 Android Studio 打开项目并直接运行 `app` 配置。


