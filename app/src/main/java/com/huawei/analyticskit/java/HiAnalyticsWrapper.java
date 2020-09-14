package com.huawei.analyticskit.java;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.huawei.hms.analytics.type.HAEventType.SUBMITSCORE;
import static com.huawei.hms.analytics.type.HAParamType.SCORE;

public class HiAnalyticsWrapper {

    private static String TAG = "HI_ANALYTICS_WRAPPER";

    private static String USER_PROFILE_TAG = "favor_sport";

    private static String EVENT_ANSWER = "Answer";
    private static String EVENT_QUESTION = "question";
    private static String EVENT_QUESTION_ANSWER = "answer";
    private static String EVENT_QUESTION_TIME = "answerTime";

    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Context mContext;
    private HiAnalyticsInstance mInstance = null;

    public HiAnalyticsWrapper(Context context) {
        mContext = context;

        // Initiate Analytics Kit
        // Enable Analytics Kit Log
        HiAnalyticsTools.enableLog();

        if (isHmsAvailable(mContext)) {
            // Generate the Analytics Instance
            mInstance = HiAnalytics.getInstance(mContext);
        }
    }

    private Boolean isHmsAvailable(Context context) {
        return HuaweiApiAvailability.getInstance()
                .isHuaweiMobileServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    /**
     * Report a customized Event
     * Event Name: Answer
     * Event Parameters:
     *  -- question: String
     *  -- answer: String
     *  -- answerTime: String
     */
    void reportAnswerEvt(String question, String answer) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

        // Initiate Parameters
        Bundle bundle = new Bundle();
        bundle.putString(EVENT_QUESTION, question);
        bundle.putString(EVENT_QUESTION_ANSWER, answer);
        bundle.putString(EVENT_QUESTION_TIME, sdf.format(new Date()));

        // Report a customized Event
        mInstance.onEvent(EVENT_ANSWER, bundle);
    }

    /**
     * Report score by using SUBMITSCORE Event
     */
    void postScore(int score) {
        // Initiate Parameters
        Bundle bundle = new Bundle();
        bundle.putLong(SCORE, score);

        // Report a predefined Event
        mInstance.onEvent(SUBMITSCORE, bundle);

    }

    void setUserProfile(String sport) {
        mInstance.setUserProfile(USER_PROFILE_TAG, sport);
    }

    void setUpUserId() {
        if (mInstance != null) {
            String id = HmsInstanceId.getInstance(mContext).getId();
            mInstance.setUserId(id);
            Log.i(TAG, "AAID: " + id);
        }
    }
}
