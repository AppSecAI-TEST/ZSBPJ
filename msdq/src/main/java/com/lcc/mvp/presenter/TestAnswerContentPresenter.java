package com.lcc.mvp.presenter;

import com.lcc.entity.Answer;
import com.lcc.entity.TestEntity;

public interface TestAnswerContentPresenter {

    void isFav(String nid);

    void Fav(Answer article, String type,String title);

    void UnFav(Answer article);
}
