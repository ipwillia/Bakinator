package viewModels;

import org.parceler.Parcel;

/**
 * Created by Ian on 12/27/2017.
 */

@Parcel
public class RecipeStepViewModel {

    public String Id;
    public String ShortDescription;
    public String FullDescription;
    public String VideoURL;
    public String ThumbnailUrl;

    public RecipeStepViewModel() {

    }

    public RecipeStepViewModel(String id, String shortDescription, String fullDescription, String videoURL, String thumbnailUrl) {
        Id = id;
        ShortDescription = shortDescription;
        FullDescription = fullDescription;
        VideoURL = videoURL;
        ThumbnailUrl = thumbnailUrl;
    }

}
