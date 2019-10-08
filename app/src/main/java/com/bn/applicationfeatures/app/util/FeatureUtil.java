package com.bn.applicationfeatures.app.util;

import com.bn.applicationfeatures.R;
import com.bn.applicationfeatures.app.model.MainFeatureModel;
import com.bn.applicationfeatures.features.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeatureUtil {

    private static HashMap<Long, Class> mapContent = new HashMap<>();

    static {
        mapContent.put(1L, DownloadFeature.class);
        mapContent.put(2L, GIFPlayerFeature.class);
        mapContent.put(3L, RecyclerViewFeature.class);
        mapContent.put(4L, AnimationTransitionFeature.class);
        mapContent.put(5L, DesignFeature.class);
    }

    public static List<MainFeatureModel> getListFeature() {
        List<MainFeatureModel> list = new ArrayList<>();

        for (int i = 0; i < mapContent.size(); i++) {
            MainFeatureModel item = new MainFeatureModel();
            item.setId((long) i + 1);
            item.setTitle(getTitle(i));
            item.setResource(getResource(i));

            list.add(item);
        }

        return list;
    }

    public static Class getClassById(long id) {
        return mapContent.get(id);
    }

    private static String getTitle(int id) {
        switch (id) {
            case 0:
                return "Download";
            case 1:
                return "GIF Player";
            case 2:
                return "RecyclerView";
            case 3:
                return "Animation Transition";
            case 4:
                return "Design";
            default:
                return "Not Available";
        }
    }

    private static int getResource(int i) {
        switch (i) {
            case 0:
                return R.drawable.ic_file_download_black_24dp;
            case 1:
                return R.drawable.ic_gif_black_24dp;
            case 2:
                return R.drawable.ic_view_list_black_24dp;
            case 3:
                return R.drawable.ic_animation;
            default:
                return R.drawable.ic_error_outline_black_24dp;
        }
    }

}
