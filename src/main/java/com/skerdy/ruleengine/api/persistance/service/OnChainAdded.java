package com.skerdy.ruleengine.api.persistance.service;

import com.skerdy.ruleengine.api.persistance.model.ChainDocument;

public interface OnChainAdded {

    void onAdd(ChainDocument document);

}
