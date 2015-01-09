package twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static junit.framework.TestCase.assertNotNull;

public class TwitterClientTest {

    static String tweet = "{\n" +
            "\t\"created_at\":\"Fri Jan 09 10:32:07 +0000 2015\",\n" +
            "\t\"id\":553499452359852032,\n" +
            "\t\"id_str\":\"553499452359852032\",\n" +
            "\t\"text\":\"RT @JoeSeyk: \\u201c@PerfectGirlsFR_: les brunes c'est 1000x mieux que les blondes http:\\/\\/t.co\\/KKhmH7YOo0\\u201d\",\n" +
            "\t\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/android\\\" rel=\\\"nofollow\\\"\\u003eTwitter for Android\\u003c\\/a\\u003e\",\n" +
            "\t\"truncated\":false,\n" +
            "\t\"in_reply_to_status_id\":1234,\n" +
            "\t\"in_reply_to_status_id_str\":\"statusId\",\n" +
            "\t\"in_reply_to_user_id\":1234,\n" +
            "\t\"in_reply_to_user_id_str\":\"userId\",\n" +
            "\t\"in_reply_to_screen_name\":\"olaNordmann\",\n" +
            "\t\"user\":\n" +
            "\t{\n" +
            "\t\t\"id\":125967882,\n" +
            "\t\t\"id_str\":\"125967882\",\n" +
            "\t\t\"name\":\"\\u039c\\u03b9c\\u03ba\\u03b1\\u03b5\\u2113 M\\u03b1\\u044f\\u03c4\\u03b9ns\",\n" +
            "\t\t\"screen_name\":\"WickaelWts\",\n" +
            "\t\t\"location\":\"Paris - Los Angeles\",\n" +
            "\t\t\"url\":\"http:\\/\\/www.facebook.com\\/MickaelMartins0\",\n" +
            "\t\t\"description\":\"Follow Me & follow Back | Instagram : Mickaelmts | Snap: mickaelusitano\",\n" +
            "\t\t\"protected\":false,\n" +
            "\t\t\"verified\":false,\n" +
            "\t\t\"followers_count\":1759,\n" +
            "\t\t\"friends_count\":87,\n" +
            "\t\t\"listed_count\":1,\n" +
            "\t\t\"favourites_count\":629,\n" +
            "\t\t\"statuses_count\":3775,\n" +
            "\t\t\"created_at\":\"Wed Mar 24 12:38:48 +0000 2010\",\n" +
            "\t\t\"utc_offset\":3600,\n" +
            "\t\t\"time_zone\":\"Paris\",\n" +
            "\t\t\"geo_enabled\":true,\n" +
            "\t\t\"lang\":\"fr\",\n" +
            "\t\t\"contributors_enabled\":false,\n" +
            "\t\t\"is_translator\":false,\n" +
            "\t\t\"profile_background_color\":\"000000\",\n" +
            "\t\t\"profile_background_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_background_images\\/592500329\\/6pgyq4gewgsag69djy93.png\",\n" +
            "\t\t\"profile_background_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_background_images\\/592500329\\/6pgyq4gewgsag69djy93.png\",\"profile_background_tile\":true,\"profile_link_color\":\"55D4EB\",\"profile_sidebar_border_color\":\"B7B709\",\"profile_sidebar_fill_color\":\"D5D516\",\"profile_text_color\":\"000000\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/547903284204535809\\/ZezvunW2_normal.jpeg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/547903284204535809\\/ZezvunW2_normal.jpeg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/125967882\\/1405353710\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweeted_status\":{\"created_at\":\"Thu Jan 08 21:48:24 +0000 2015\",\"id\":553307258042732544,\"id_str\":\"553307258042732544\",\"text\":\"\\u201c@PerfectGirlsFR_: les brunes c'est 1000x mieux que les blondes http:\\/\\/t.co\\/KKhmH7YOo0\\u201d\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":550383947260370944,\"in_reply_to_status_id_str\":\"550383947260370944\",\"in_reply_to_user_id\":2925407973,\"in_reply_to_user_id_str\":\"2925407973\",\"in_reply_to_screen_name\":\"PerfectGirlsFR_\",\"user\":{\"id\":2879990013,\"id_str\":\"2879990013\",\"name\":\"\\u2022KENDR!CK\\u2022\",\"screen_name\":\"JoeSeyk\",\"location\":\"\",\"url\":null,\"description\":\"Vasi rien \\u00e0 dire...appart Snap: j0j075 et Insta: JoeSeyk #TeamPSG ! XV\\u00e8\",\"protected\":false,\"verified\":false,\"followers_count\":478,\"friends_count\":477,\"listed_count\":1,\"favourites_count\":41,\"statuses_count\":182,\"created_at\":\"Sun Nov 16 20:27:12 +0000 2014\",\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"lang\":\"fr\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"C0DEED\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_link_color\":\"0084B4\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/545278162201219072\\/5BwXoTtu_normal.jpeg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/545278162201219072\\/5BwXoTtu_normal.jpeg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/2879990013\\/1420748782\",\"default_profile\":true,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":1,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[],\"user_mentions\":[{\"screen_name\":\"PerfectGirlsFR_\",\"name\":\"Le r\\u00eave.\",\"id\":2925407973,\"id_str\":\"2925407973\",\"indices\":[1,17]}],\"symbols\":[],\"media\":[{\"id\":550383937042669568,\"id_str\":\"550383937042669568\",\"indices\":[64,86],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"url\":\"http:\\/\\/t.co\\/KKhmH7YOo0\",\"display_url\":\"pic.twitter.com\\/KKhmH7YOo0\",\"expanded_url\":\"http:\\/\\/twitter.com\\/PerfectGirlsFR_\\/status\\/550383947260370944\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":340,\"h\":340,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":640,\"h\":640,\"resize\":\"fit\"},\"medium\":{\"w\":600,\"h\":600,\"resize\":\"fit\"}},\"source_status_id\":550383947260370944,\"source_status_id_str\":\"550383947260370944\"}]},\"extended_entities\":{\"media\":[{\"id\":550383937042669568,\"id_str\":\"550383937042669568\",\"indices\":[64,86],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"url\":\"http:\\/\\/t.co\\/KKhmH7YOo0\",\"display_url\":\"pic.twitter.com\\/KKhmH7YOo0\",\"expanded_url\":\"http:\\/\\/twitter.com\\/PerfectGirlsFR_\\/status\\/550383947260370944\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":340,\"h\":340,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":640,\"h\":640,\"resize\":\"fit\"},\"medium\":{\"w\":600,\"h\":600,\"resize\":\"fit\"}},\"source_status_id\":550383947260370944,\"source_status_id_str\":\"550383947260370944\"}]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"low\",\"lang\":\"fr\"},\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[],\"user_mentions\":[{\"screen_name\":\"JoeSeyk\",\"name\":\"\\u2022KENDR!CK\\u2022\",\"id\":2879990013,\"id_str\":\"2879990013\",\"indices\":[3,11]},{\"screen_name\":\"PerfectGirlsFR_\",\"name\":\"Le r\\u00eave.\",\"id\":2925407973,\"id_str\":\"2925407973\",\"indices\":[14,30]}],\"symbols\":[],\"media\":[{\"id\":550383937042669568,\"id_str\":\"550383937042669568\",\"indices\":[77,99],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"url\":\"http:\\/\\/t.co\\/KKhmH7YOo0\",\"display_url\":\"pic.twitter.com\\/KKhmH7YOo0\",\"expanded_url\":\"http:\\/\\/twitter.com\\/PerfectGirlsFR_\\/status\\/550383947260370944\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":340,\"h\":340,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":640,\"h\":640,\"resize\":\"fit\"},\"medium\":{\"w\":600,\"h\":600,\"resize\":\"fit\"}},\"source_status_id\":550383947260370944,\"source_status_id_str\":\"550383947260370944\"}]},\"extended_entities\":{\"media\":[{\"id\":550383937042669568,\"id_str\":\"550383937042669568\",\"indices\":[77,99],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/B6NbRlGCYAAyKWn.jpg\",\"url\":\"http:\\/\\/t.co\\/KKhmH7YOo0\",\"display_url\":\"pic.twitter.com\\/KKhmH7YOo0\",\"expanded_url\":\"http:\\/\\/twitter.com\\/PerfectGirlsFR_\\/status\\/550383947260370944\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"small\":{\"w\":340,\"h\":340,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"large\":{\"w\":640,\"h\":640,\"resize\":\"fit\"},\"medium\":{\"w\":600,\"h\":600,\"resize\":\"fit\"}},\"source_status_id\":550383947260370944,\"source_status_id_str\":\"550383947260370944\"}]},\n" +
            "\t\t\"favorited\":false,\n" +
            "\t\t\"retweeted\":false,\n" +
            "\t\t\"possibly_sensitive\":false,\n" +
            "\t\t\"filter_level\":\"medium\",\n" +
            "\t\t\"lang\":\"fr\",\n" +
            "\t\t\"timestamp_ms\":\"1420799527622\"}\n" +
            "\t\"retweeted_status\":\n" +
            "\t{\n" +
            "\t\t\"id\":551213330895892481\"\n}";

    @Test
    public void testSerializer() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());
        mapper.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH));

        Tweet deSerializedTweet = mapper.readValue(tweet, Tweet.class);

        assertNotNull(StringUtils.isBlank(deSerializedTweet.getTweetId()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getInReplyToScreenName()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getInReplyToStatusId()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getInReplyToStatusIdStr()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getInReplyToUserId()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getInReplyToUserIdStr()));
        assertNotNull(StringUtils.isBlank(deSerializedTweet.getRetweetedStatus().getTweetId()));
    }
}