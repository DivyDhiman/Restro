package controllerAll;


/**
 * Created by Abhay dhiman
 */

// All Constant Data Method
public class Config {

    public static final String Four_Square_Parent_URL = "https://api.foursquare.com/v2/venues/explore?ll=";
    public static final String Four_Square_GET_NEAREST_LOCATION = "&venuePhotos=1&client_id=3P1KGZPYB5XETWEGOAS235WEZTGFTHOF11CB3AXM2YDVRI3N&client_secret=AB5UEOONXC1TGOGZPS5LDOFGUVF4ZAU21SF5BGQELQXMRBHF&categoryId=";
    public static final String Four_Square_Version = "&v=20161304";
//    public static final String Four_Square_GET_NEAREST_LOCATION = "&venuePhotos=1&client_id=3P1KGZPYB5XETWEGOAS235WEZTGFTHOF11CB3AXM2YDVRI3N&client_secret=AB5UEOONXC1TGOGZPS5LDOFGUVF4ZAU21SF5BGQELQXMRBHF&categoryId=4d4b7105d754a06374d81259&v=20161304";


    public static final String FOUR_SQUARE_PARENT_UrL_DETAIL = "https://api.foursquare.com/v2/venues/";
    public static final String FOUR_SQUARE_DETAIL_API = "?&client_id=3P1KGZPYB5XETWEGOAS235WEZTGFTHOF11CB3AXM2YDVRI3N&client_secret=AB5UEOONXC1TGOGZPS5LDOFGUVF4ZAU21SF5BGQELQXMRBHF&v=20151005&limit=100";
    public static final String FOUR_SQUARE_REVIEWS_API = "/tips?&client_id=3P1KGZPYB5XETWEGOAS235WEZTGFTHOF11CB3AXM2YDVRI3N&client_secret=AB5UEOONXC1TGOGZPS5LDOFGUVF4ZAU21SF5BGQELQXMRBHF&v=20151005&limit=100";



    public static final String googleDirection1 = "http://maps.googleapis.com/maps/api/directions/json?origin=";
    public static final String getGoogleDirection2 = "&destination=";
    public static final String getGoogleDirection3 = "&sensor=false&mode=driving";

//    String s = "http://maps.googleapis.com/maps/api/directions/json?origin="+latitude+","+longitude+"&destination="+latdes+","+lngdes+"&sensor=false&mode=driving";

    public static final String BASE_URL = "http://52.55.26.160/foodapp/";
    public static final String User_Service = "UserService.svc/";
    public static final String Get_Email_Id_Availability = "GetEmailIdAvailability/";
    public static final String Register_User = "RegisterUser/";
    public static final String User_Logout = "UserLogout/";
    public static final String Get_Category = "GetCategory/";
    public static final String Reviews = "Reviews.svc/";
    public static final String Post_Review = "PostReview";
    public static final String User_Login = "UserLogin/";
    public static final String Forgot_Password = "Forgotpassword/";
    public static final String Get_Review_List = "GetReviewList/";
    public static final String ABOUT_HTML = "about.html";


    //Default all food category
    public static final String DEFAULT_FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259";
    public static final int DEFAULT_RADIUS = 4000;
    public static final int DEFAULT_MIN_RADIUS = 800;


    //Folder name Hiddin and Gallery
    public final static String APPNAME = "MarketChat";
    public static final String FOLDER_NAME = "MarketChat";
    public static final String FOLDER_NAME_GALLERY = "MarketChat";
    public static final String PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String SEND_IMAGE = "SEND IMAGE";
    public static final String SEND_VIDEO = "SEND VIDEO";
    public static final String EXTRA_FOLDER = "EXTRA_FOLDER";
    public static final String THUMB_VIDEO_EXT = "thumbnail.jpg";

    public static final String NO_DATA = "NO DATA";

}