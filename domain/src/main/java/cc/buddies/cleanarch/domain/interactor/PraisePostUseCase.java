package cc.buddies.cleanarch.domain.interactor;

import cc.buddies.cleanarch.domain.interactor.type.CompletableUseCaseWithParameter;
import cc.buddies.cleanarch.domain.repository.PostRepository;
import cc.buddies.cleanarch.domain.request.PraisePostParams;
import io.reactivex.rxjava3.core.Completable;

public class PraisePostUseCase implements CompletableUseCaseWithParameter<PraisePostParams> {

    private PostRepository postRepository;

    public PraisePostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Completable execute(PraisePostParams parameter) {
        return this.postRepository.praisePost(parameter.getPostId(), parameter.getUserId(), parameter.isAddOrCancel());
    }

}
