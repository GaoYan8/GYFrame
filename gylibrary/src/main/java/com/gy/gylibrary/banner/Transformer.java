package com.gy.gylibrary.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.gy.gylibrary.banner.transformer.AccordionTransformer;
import com.gy.gylibrary.banner.transformer.BackgroundToForegroundTransformer;
import com.gy.gylibrary.banner.transformer.CubeInTransformer;
import com.gy.gylibrary.banner.transformer.CubeOutTransformer;
import com.gy.gylibrary.banner.transformer.DefaultTransformer;
import com.gy.gylibrary.banner.transformer.DepthPageTransformer;
import com.gy.gylibrary.banner.transformer.FlipHorizontalTransformer;
import com.gy.gylibrary.banner.transformer.FlipVerticalTransformer;
import com.gy.gylibrary.banner.transformer.ForegroundToBackgroundTransformer;
import com.gy.gylibrary.banner.transformer.RotateDownTransformer;
import com.gy.gylibrary.banner.transformer.RotateUpTransformer;
import com.gy.gylibrary.banner.transformer.ScaleInOutTransformer;
import com.gy.gylibrary.banner.transformer.StackTransformer;
import com.gy.gylibrary.banner.transformer.TabletTransformer;
import com.gy.gylibrary.banner.transformer.ZoomInTransformer;
import com.gy.gylibrary.banner.transformer.ZoomOutSlideTransformer;
import com.gy.gylibrary.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
