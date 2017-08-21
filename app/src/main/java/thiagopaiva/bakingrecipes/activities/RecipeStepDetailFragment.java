package thiagopaiva.bakingrecipes.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import thiagopaiva.bakingrecipes.R;
import thiagopaiva.bakingrecipes.objects.RecipeStep;
import thiagopaiva.bakingrecipes.utils.ExoPlayerVideoHandler;

public class RecipeStepDetailFragment extends Fragment {

    public static final String ARG_STEP = "step";
    private boolean destroyVideo = false;
    private RecipeStep step;

    @BindView(R.id.step_description) TextView stepDesc;
    @BindView(R.id.step_thumbnail) ImageView stepThumbnail;
    @BindView(R.id.step_video) SimpleExoPlayerView stepVideoPlayer;

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_STEP)) {
            step = getArguments().getParcelable(ARG_STEP);
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(step.getShortDescription());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail, container, false);
        ButterKnife.bind(this,rootView);
        fillUI(rootView);
        return rootView;
    }

    private void fillUI(View view){
        if (step != null) {
            stepDesc.setText(step.getDescription());
            if(step.getVideoURL() != null && stepVideoPlayer != null){
                if(step.getVideoURL().isEmpty())
                    stepVideoPlayer.setVisibility(View.GONE);
                else {
                    stepVideoPlayer.setVisibility(View.VISIBLE);
                    if(ExoPlayerVideoHandler.getInstance().getPlayer() != null)
                        stepVideoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());
                    else
                        ExoPlayerVideoHandler.getInstance()
                                .prepareExoPlayerForUri(view.getContext(),
                                        Uri.parse(step.getVideoURL()), stepVideoPlayer);
                    ExoPlayerVideoHandler.getInstance().goToForeground();
                }
            } else {
                if(stepVideoPlayer != null)
                    stepVideoPlayer.setVisibility(View.GONE);
                String thumbnail = step.getThumbnailURL();
                if(!thumbnail.isEmpty()){
                    stepThumbnail.setVisibility(View.VISIBLE);
                    Glide.with(getContext())
                            .load(thumbnail)
                            .placeholder(R.drawable.ic_load)
                            .into(stepThumbnail);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }
}
