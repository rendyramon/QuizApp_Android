package com.amcolabs.quizapp.screens;

import java.util.ArrayList;
import java.util.List;

import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amcolabs.quizapp.AppController;
import com.amcolabs.quizapp.R;
import com.amcolabs.quizapp.Screen;
import com.amcolabs.quizapp.adapters.CategoryItemListAdapter;
import com.amcolabs.quizapp.adapters.QuizItemListAdapter;
import com.amcolabs.quizapp.appcontrollers.UserMainPageController;
import com.amcolabs.quizapp.databaseutils.Category;
import com.amcolabs.quizapp.databaseutils.Quiz;
import com.amcolabs.quizapp.datalisteners.DataInputListener;
import com.amcolabs.quizapp.uiutils.UiUtils.UiText;
import com.amcolabs.quizapp.widgets.GothamTextView;

public class HomeScreen extends Screen { 
	List<Category> categories = new ArrayList<Category>();
	private UserMainPageController userMainController;
	public HomeScreen(AppController appController) {
		super(appController);
		userMainController = (UserMainPageController)appController;
	} 

	public void addCategoriesView(List<Category> categories, boolean showViewMore) {
		this.categories = categories;
		
		CategoryItemListAdapter categoryAdaptor = new CategoryItemListAdapter(getApp(),0,categories,new DataInputListener<Category>(){
			@Override
			public String onData(Category s) {
				userMainController.onCategorySelected(s);
				return null;
			}
		});
		
		LinearLayout lView = (LinearLayout) getApp().getActivity().getLayoutInflater().inflate(R.layout.block_list_view, null);
		lView.setBackgroundColor(getApp().getConfig().getAThemeColor());
		TextView title = (TextView) lView.findViewById(R.id.title_text_view);
		title.setText(UiText.CATEGORIES.getValue());
		lView.findViewById(R.id.search_text).setVisibility(View.GONE);
		FrameLayout viewMore = (FrameLayout) lView.findViewById(R.id.view_all_wrapper);
		if(!showViewMore){
			viewMore.setVisibility(View.GONE);
		}
		else{
			viewMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { 
					userMainController.showAllCategories();
				}
			});
		}
		((ListView) lView.findViewById(R.id.listView)).setAdapter(categoryAdaptor);
		addView(lView);
	}

	public void addUserQuizzesView(List<Quiz> quizzes, boolean showViewMore) {
		final QuizItemListAdapter quizAdaptor = new QuizItemListAdapter(getApp(),0,quizzes, new DataInputListener<Quiz>(){
			@Override
			public String onData(Quiz quiz) {
				userMainController.onQuizSelected(quiz);
				return null;
			}
		});
		LinearLayout lView = (LinearLayout) getApp().getActivity().getLayoutInflater().inflate(R.layout.block_list_view, null);
		EditText searchText = (EditText) lView.findViewById(R.id.search_text);
		searchText.setVisibility(View.GONE);
		GothamTextView titleView = (GothamTextView) lView.findViewById(R.id.title_text_view);
		titleView.setText(UiText.USER_FAVOURITES.getValue());
		((ListView) lView.findViewById(R.id.listView)).setAdapter(quizAdaptor);
		FrameLayout viewMore = (FrameLayout) lView.findViewById(R.id.view_all_wrapper);
		if(!showViewMore){
			viewMore.setVisibility(View.GONE);
		}
		else{
			viewMore.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { 
					userMainController.showAllUserQuizzes();
				}
			});
		}

		
		
		addView(lView);
	}
}