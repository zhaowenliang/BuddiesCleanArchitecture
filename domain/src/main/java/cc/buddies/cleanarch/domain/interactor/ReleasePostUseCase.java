package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.SingleUseCaseWithParameter;
import cc.buddies.cleanarch.domain.model.PostModel;
import cc.buddies.cleanarch.domain.repository.PostRepository;
import cc.buddies.cleanarch.domain.request.ReleasePostParams;
import io.reactivex.rxjava3.core.Single;

public class ReleasePostUseCase implements SingleUseCaseWithParameter<ReleasePostParams, PostModel> {

    private PostRepository postRepository;

    public ReleasePostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Single<PostModel> execute(ReleasePostParams parameter) {
        return this.postRepository.releasePost(parameter);
    }
}
