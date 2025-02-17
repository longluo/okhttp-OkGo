/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzy.demo.okdownload;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.demo.R;
import com.lzy.demo.base.BaseActivity;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DownloadAllActivity extends BaseActivity implements XExecutor.OnAllTaskEndListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private DownloadAdapter adapter;
    private OkDownload okDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_all);
        initToolBar(toolbar, true, "所有任务");

        okDownload = OkDownload.getInstance();
        adapter = new DownloadAdapter(this);
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        okDownload.addOnAllTaskEndListener(this);
    }

    @Override
    public void onAllTaskEnd() {
        showToast("所有下载任务已结束");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
        adapter.unRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.removeAll)
    public void removeAll(View view) {
        okDownload.removeAll();
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.pauseAll)
    public void pauseAll(View view) {
        okDownload.pauseAll();
    }

    @OnClick(R.id.startAll)
    public void startAll(View view) {
        okDownload.startAll();
    }
}
