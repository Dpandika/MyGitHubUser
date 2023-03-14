package com.example.mygithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithubuser.databinding.ActivityDetailBinding

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    showLoading(false)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = it.followers
                    tvFollowing.text = it.following
                    tvLocation.text = it.location
                    tvCompany.text = it.company
                    tvRepository.text = it.public_repos
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imgAvatar)
                }
            }
        }

        val sectionsPagerAdapter = SectionPageAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(SectionPageAdapter.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}