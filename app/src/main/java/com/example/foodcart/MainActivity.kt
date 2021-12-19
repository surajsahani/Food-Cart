package com.example.foodcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.foodblogs.adapter.FoodAdapter
import com.example.foodblogs.adapter.SliderAdapter
import com.example.foodblogs.model.BaseResponse
import com.example.foodblogs.model.Card
import com.example.foodblogs.retrofit.ApiResult
import com.example.foodblogs.utils.Extensions
import com.example.foodblogs.utils.Extensions.setImage
import com.example.foodblogs.utils.ProgressUtils
import com.example.foodblogs.viewmodel.MainViewModel
import com.example.foodcart.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var _binding : ActivityMainBinding
    private lateinit var _mainViewModel: MainViewModel
    private lateinit var _sliderAdapter: SliderAdapter

    private val _detailBlogBehaviour: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(_binding.bottomSheetBlogDetail.bmRootCoordinator as View) }

    private val sliderImageList = mutableListOf<String>()
    private var foodCardList = mutableListOf<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initializingVariables()
        setOnClickListeners()
        getFoodDetailsFromApi()
        _binding.bottomSheetBlogDetail.viewPager.registerOnPageChangeCallback(viewPagerCallBack)
    }

    private fun initializingVariables() {
        _mainViewModel = MainViewModel()
        _sliderAdapter = SliderAdapter()
        _binding.bottomSheetBlogDetail.viewPager.adapter = _sliderAdapter
        _binding.bottomSheetBlogDetail.foodRecycler.adapter = _foodAdapter
    }

    private fun setOnClickListeners() {
        _binding.cvDashboard.setOnClickListener(this)
    }

    private fun getFoodDetailsFromApi() {
        _mainViewModel.getFoodDetails().observe(this,{
            when(it){
                is ApiResult.Success -> {
                    ProgressUtils.closeProgressIndicator()
                    when(it.data){
                        is BaseResponse -> {
                            val data = it.data.data
                            val sortedCardList = sortByCardNo(data.card)
                            _binding.apply {
                                tvCardHeading2.text = data.card_details.title +" " + data.card_details.city
                                tvFoodOneTitle.text = sortedCardList[0].title
                                tvFoodOneDescription.text = sortedCardList[0].desc
                                ivFoodOne.setImage(sortedCardList[0].img)
                                tvFoodTwoTitle.text = sortedCardList[1].title
                                tvFoodTwoDescription.text = sortedCardList[1].desc
                                ivFoodTwo.setImage(sortedCardList[1].img)
                                for(item in data.card){ sliderImageList.add(item.img) }
                            }
                            foodCardList = sortByCardNo(data.card)
                            for(item in foodCardList){

                            }
                        }
                    }
                }
                is ApiResult.Failure -> {
                    Extensions.errorShortSnackBar(_binding.root,getString(R.string.failure_error))
                    ProgressUtils.closeProgressIndicator()
                }
                is ApiResult.NoData -> ProgressUtils.closeProgressIndicator()
                is ApiResult.InProgress -> ProgressUtils.showProgressIndicator(this)
            }
        })
    }

    private val _foodAdapter: FoodAdapter by lazy { FoodAdapter(mutableListOf()) { _, _, card ->
        //Handling slider image based on click action
        _binding.bottomSheetBlogDetail.viewPager.currentItem = card.card_no -1
    } }

    //Callback for dot indicators
    private val viewPagerCallBack = object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            when(position){
                0 -> updateDotUI(dotOne = true,dotTwo = false,dotThree = false,dotFour = false)
                1 -> updateDotUI(dotOne = false,dotTwo = true,dotThree = false,dotFour = false)
                2 -> updateDotUI(dotOne = false,dotTwo = false,dotThree = true,dotFour = false)
                3 -> updateDotUI(dotOne = false,dotTwo = false,dotThree = false,dotFour = true)
            }
        }
    }

    private fun updateDotUI(dotOne:Boolean,dotTwo:Boolean,dotThree:Boolean,dotFour:Boolean){
        setImageUI(dotOne,_binding.bottomSheetBlogDetail.ivDot1)
        setImageUI(dotTwo,_binding.bottomSheetBlogDetail.ivDot2)
        setImageUI(dotThree,_binding.bottomSheetBlogDetail.ivDot3)
        setImageUI(dotFour,_binding.bottomSheetBlogDetail.ivDot4)
    }

    private fun setImageUI(dotAction: Boolean,imageView: ImageView) {
        when(dotAction){
            true -> imageView.setImageResource(R.drawable.ic_baseline_circle_24_black)
            false -> imageView.setImageResource(R.drawable.ic_baseline_circle_24)
        }
    }

    //Sort using collections
    private fun sortByCardNo(list: List<Card>) : MutableList<Card>{
        val sortedList = list.map { it.copy() }.toMutableList()
        sortedList.sortBy { it.card_no }
        return sortedList
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cv_dashboard -> {
                _detailBlogBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
                _sliderAdapter.updateList(sliderImageList)
                _foodAdapter.updateList(foodCardList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.bottomSheetBlogDetail.viewPager.unregisterOnPageChangeCallback(viewPagerCallBack)
    }

}