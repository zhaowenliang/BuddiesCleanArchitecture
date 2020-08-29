package cc.buddies.cleanarch.domain.interactor;

import java.util.List;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.repository.PostRepository;
import io.reactivex.rxjava3.core.Single;

public class GetPostsUseCase implements SingleUseCaseWithParameter<Integer, List<PostModel>> {

    private PostRepository postRepository;

    public GetPostsUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Single<List<PostModel>> execute(Integer parameter) {
        return this.postRepository.posts(parameter);
    }

}
