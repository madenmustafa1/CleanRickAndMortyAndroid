package com.maden.cleanrickandmortyandroid.presentation.home_page

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.maden.cleanrickandmortyandroid.R
import com.maden.cleanrickandmortyandroid.common.DataResource
import com.maden.cleanrickandmortyandroid.databinding.FragmentHomePageBinding
import com.maden.cleanrickandmortyandroid.domain.model.characters.CharacterModel
import com.maden.cleanrickandmortyandroid.presentation.home_page.widgets.CardViewAnimHelper
import com.maden.cleanrickandmortyandroid.presentation.widgets.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private var cardViewAnimHelper: CardViewAnimHelper? = null

    private val _homePageViewModel: HomePageViewModel by viewModels()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomePageBinding.bind(view)

        requireActivity().actionBar?.show()

        initViews()
        observeData()
        _homePageViewModel.getCharactersData()
    }

    private fun initViews() {
        cardViewAnimHelper = CardViewAnimHelper(
            _context = requireContext(),
            _cardView = binding.cardView
        ) {
            _homePageViewModel.fetchNextCharacter()
        }

        binding.likeButton.setOnClickListener {
            cardViewAnimHelper?.likeAnim()
        }

        binding.dislikeButton.setOnClickListener {
            cardViewAnimHelper?.disLikeAnim()
        }

        binding.starButton.setOnClickListener {
            if (cardViewAnimHelper?.likeAnim() == true) {
                binding.lottieAnimationLayout.confetti.visibility = View.VISIBLE
                binding.lottieAnimationLayout.confetti.playAnimation()
            }
        }

        binding.lottieAnimationLayout.confetti.addAnimatorListener(
            object :
                Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    binding.lottieAnimationLayout.confetti.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }
        )
    }

    private fun observeData() {
        _homePageViewModel.charactersData.observe(viewLifecycleOwner) {
            binding.lottieAnimationLayout.loading.visibility = View.GONE
            binding.lottieAnimationLayout.httpError.visibility = View.GONE
            when (it) {
                is DataResource.Loading -> {
                    binding.lottieAnimationLayout.loading.visibility = View.VISIBLE
                }
                is DataResource.Error -> {
                    binding.lottieAnimationLayout.httpError.visibility = View.VISIBLE
                    requireContext().showAlertDialog(
                        title = getString(R.string.error_data),
                        message = getString(R.string.try_again)
                    ) {
                        _homePageViewModel.getCharactersData()
                    }
                }

                is DataResource.Success -> {
                    binding.cardView.visibility = View.VISIBLE
                    setCharacterDetail(it.data ?: return@observe)
                }
            }
        }
    }

    private fun setCharacterDetail(model: CharacterModel) {
        binding.apply {
            characterName.text = model.characterName
            characterStatus.text = model.status
            characterPhoto
            characterLastKnownLocation.text = model.lastKnowLocation.name

            _homePageViewModel.downloadBitmap(
                url = model.characterPhoto,
                exception = {},
                bitmap = { characterPhoto.setImageBitmap(it) }
            )
        }
    }

}