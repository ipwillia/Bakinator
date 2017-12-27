package viewModels;

/**
 * Created by Ian on 12/27/2017.
 */

public class RecipeStepViewModel {

    int Id;
    String ShortDescription;
    String FullDescription;
    String VideoURL;
    String ThumbnailUrl;

    public RecipeStepViewModel() {

    }

    public RecipeStepViewModel(int id, String shortDescription, String fullDescription, String videoURL, String thumbnailUrl) {
        Id = id;
        ShortDescription = shortDescription;
        FullDescription = fullDescription;
        VideoURL = videoURL;
        ThumbnailUrl = thumbnailUrl;
    }

}
