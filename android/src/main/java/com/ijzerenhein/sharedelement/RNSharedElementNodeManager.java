package com.ijzerenhein.sharedelement;

import java.util.Map;
import java.util.HashMap;

import android.view.View;
import android.content.Context;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.NativeViewHierarchyManager;

class RNSharedElementNodeManager {
  private Map<Integer, RNSharedElementNode> mNodes = new HashMap<Integer, RNSharedElementNode>();
  private NativeViewHierarchyManager mNativeViewHierarchyManager;
  private Context mContext;

  RNSharedElementNodeManager(Context context) {
    mContext = context;
  }

  void setNativeViewHierarchyManager(NativeViewHierarchyManager nativeViewHierarchyManager) {
    mNativeViewHierarchyManager = nativeViewHierarchyManager;
  }

  NativeViewHierarchyManager getNativeViewHierarchyManager() {
    return mNativeViewHierarchyManager;
  }

  RNSharedElementNode acquire(int reactTag, View view, boolean isParent, View ancestor, ReadableMap styleConfig) {
    synchronized (mNodes) {
      RNSharedElementNode node = mNodes.get(reactTag);
      if (node != null) {
        node.addRef();
        return node;
      }
      node = new RNSharedElementNode(mContext, reactTag, view, isParent, ancestor, styleConfig);
      mNodes.put(reactTag, node);
      return node;
    }
  }

  int release(RNSharedElementNode node) {
    synchronized (mNodes) {
      int refCount = node.releaseRef();
      if (refCount == 0) {
        mNodes.remove(node.getReactTag());
      }
      return refCount;
    }
  }
}