package com.ijzerenhein.sharedelement;

import android.view.View;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReactApplicationContext;

public class RNSharedElementTransitionManager extends SimpleViewManager<RNSharedElementTransition> {
    private ReactApplicationContext mReactContext;
    private RNSharedElementNodeManager mNodeManager;

    public static final String REACT_CLASS = "RNSharedElementTransition";

    RNSharedElementTransitionManager(ReactApplicationContext reactContext, RNSharedElementNodeManager nodeManager) {
        super();
        mReactContext = reactContext;
        mNodeManager = nodeManager;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNSharedElementTransition createViewInstance(ThemedReactContext context) {
        return new RNSharedElementTransition(context, mNodeManager);
    }

    @Override
    public void onDropViewInstance(RNSharedElementTransition view) {
        super.onDropViewInstance(view);
        view.releaseData();
    }

    @ReactProp(name = "nodePosition")
    public void setNodePosition(final RNSharedElementTransition view, final float nodePosition) {
        view.setNodePosition(nodePosition);
    }

    @ReactProp(name = "animation")
    public void setAnimation(final RNSharedElementTransition view, final int animation) {
        view.setAnimation(RNSharedElementTransition.Animation.values()[animation]);
    }

    @ReactProp(name = "resize")
    public void setResize(final RNSharedElementTransition view, final int resize) {
        view.setResize(RNSharedElementTransition.Resize.values()[resize]);
    }

    @ReactProp(name = "align")
    public void setAlign(final RNSharedElementTransition view, final int align) {
        view.setAlign(RNSharedElementTransition.Align.values()[align]);
    }

    private void setViewItem(final RNSharedElementTransition view, RNSharedElementTransition.Item item, final ReadableMap map) {
        if (map == null) return;
        if (!map.hasKey("node") || !map.hasKey("ancestor")) return;
        final ReadableMap nodeMap = map.getMap("node");
        final ReadableMap ancestorMap = map.getMap("ancestor");
        int nodeHandle = nodeMap.getInt("nodeHandle");
        int ancestorHandle = ancestorMap.getInt("nodeHandle");
        boolean isParent = nodeMap.getBoolean("isParent");
        ReadableMap styleConfig = nodeMap.getMap("nodeStyle");
        View nodeView = mNodeManager.getNativeViewHierarchyManager().resolveView(nodeHandle);
        View ancestorView = mNodeManager.getNativeViewHierarchyManager().resolveView(ancestorHandle);
        RNSharedElementNode node = mNodeManager.acquire(nodeHandle, nodeView, isParent, ancestorView, styleConfig);
        view.setItemNode(item, node);
    }

    @ReactProp(name = "startNode")
    public void setStartNode(final RNSharedElementTransition view, final ReadableMap startNode) {
        setViewItem(view, RNSharedElementTransition.Item.START, startNode);
    }

    @ReactProp(name = "endNode")
    public void setEndNode(final RNSharedElementTransition view, final ReadableMap endNode) {
        setViewItem(view, RNSharedElementTransition.Item.END, endNode);
    }
}