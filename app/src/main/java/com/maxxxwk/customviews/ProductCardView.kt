package com.maxxxwk.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.maxxxwk.customviews.databinding.ProductCardBinding

class ProductCardView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var binding: ProductCardBinding =
        ProductCardBinding.bind(inflate(context, R.layout.product_card, this))

    var productImageDrawable: Drawable?
        get() = binding.ivProductImage.drawable
        set(value) {
            value?.let {
                binding.ivProductImage.setImageDrawable(it)
            }
        }
    var productImageResource = DEFAULT_PRODUCT_IMAGE_RESOURCE
        set(@DrawableRes value) {
            binding.ivProductImage.setImageResource(value)
        }
    var title: String
        get() = binding.tvTitle.text.toString()
        set(value) {
            binding.tvTitle.text = value
        }
    var subtitle: String
        get() = binding.tvSubtitle.text.toString()
        set(value) {
            binding.tvSubtitle.text = value
        }
    var price = DEFAULT_PRICE
        set(value) {
            binding.tvProductPrice.text = resources.getString(R.string.price_text_view, value)
            field = value
        }
    var rating: Float
        get() = binding.rbProductRating.rating
        set(value) {
            binding.rbProductRating.rating = value
        }


    init {
        val attributes = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ProductCardView
        )
        with(binding) {
            ivProductImage.setImageResource(
                attributes.getResourceId(
                    R.styleable.ProductCardView_productImage,
                    DEFAULT_PRODUCT_IMAGE_RESOURCE
                )
            )
            tvTitle.text = attributes.getString(R.styleable.ProductCardView_titleText)
            tvTitle.textSize = attributes.getDimension(
                R.styleable.ProductCardView_titleTextSize,
                resources.getDimension(R.dimen.product_card_title_TextView_default_textSize)
            )
            tvSubtitle.text = attributes.getString(R.styleable.ProductCardView_subtitleText)
            tvSubtitle.textSize = attributes.getDimension(
                R.styleable.ProductCardView_subtitleTextSize,
                resources.getDimension(R.dimen.product_card_subtitle_TextView_default_textSize)
            )
            tvProductPrice.text = resources.getString(
                R.string.price_text_view, attributes.getFloat(
                    R.styleable.ProductCardView_price,
                    DEFAULT_PRICE
                )
            )
            tvProductPrice.textSize = attributes.getDimension(
                R.styleable.ProductCardView_priceTextSize,
                resources.getDimension(R.dimen.product_card_price_TextView_default_textSize)
            )
            rbProductRating.rating =
                attributes.getFloat(R.styleable.ProductCardView_rating, DEFAULT_RATING)
        }
        attributes.recycle()
    }

    companion object {
        private const val DEFAULT_PRODUCT_IMAGE_RESOURCE = R.mipmap.ic_launcher
        private const val DEFAULT_PRICE = 0f
        private const val DEFAULT_RATING = 0f
    }

    fun setTitleTextSize(@DimenRes res: Int) {
        binding.tvTitle.textSize = resources.getDimension(res)
    }

    fun setSubtitleTextSize(@DimenRes res: Int) {
        binding.tvSubtitle.textSize = resources.getDimension(res)
    }

    fun setPriceTextSize(@DimenRes res: Int) {
        binding.tvProductPrice.textSize = resources.getDimension(res)
    }

    fun setTitleText(@StringRes res: Int) {
        binding.tvTitle.setText(res)
    }

    fun setSubtitleText(@StringRes res: Int) {
        binding.tvSubtitle.setText(res)
    }
}