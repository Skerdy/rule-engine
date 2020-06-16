package com.tecnositaf.rayonit.ruleengine.api.persistance.service;

import com.tecnositaf.rayonit.ruleengine.api.persistance.model.ChainDocument;

public interface OnChainAdded {

    void onAdd(ChainDocument document);

}
