package com.connect.core.service;

import com.connect.api.follow.dto.FollowDto;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.follow.iml.FollowServiceImpl;
import com.connect.data.entity.Follow;
import com.connect.data.entity.User;
import com.connect.data.repository.IFollowRepository;
import com.connect.data.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = FollowServiceImpl.class)
public class FollowServiceTest {
    @MockBean
    private IFollowRepository followRepository;

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private FollowServiceImpl followService;

    @Captor
    private ArgumentCaptor<Follow> captor;

    @Test
    public void test_follow_public_user_should_approve() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(followerId, followingId)).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.follow(followDto);

        verify(followRepository, times(1)).createFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }

    @Test
    public void test_follow_semi_user_should_pending() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.SEMI.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(followerId, followingId)).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.follow(followDto);

        verify(followRepository, times(1)).createFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.PENDING.getCode(), follow.getStatus());
    }

    @Test
    public void test_follow_private_user_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PRIVATE.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(followerId, followingId)).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.follow(followDto);
        });
        assertEquals(
                "Not permitted for this request:target userId not available for following: " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_follow_deleted_user_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(followerId, followingId)).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.follow(followDto);
        });
        assertEquals(
                "Not permitted for this request:target userId not available for following: " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_follow_non_existed_user_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(null);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.follow(followDto);
        });
        assertEquals(
                "Parameters error:follower not existed: " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_unfollow_following_user_should_unfollow() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId
        )).thenReturn(true);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.unfollow(followDto);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.UNFOLLOW.getCode(), follow.getStatus());
    }

    @Test
    public void test_unfollow_not_following_user_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";

        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.unfollow(followDto);
        });
        assertEquals(
                "Follow not existed.:UNFOLLOW FAILED! followerId is not following " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_unfollow_non_existed_user_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(null);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.unfollow(followDto);
        });
        assertEquals(
                "Follow not existed.:UNFOLLOW FAILED! followerId is not following " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_approve_pending_request_should_update_follower_to_approved() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.SEMI.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.PENDING.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId
        )).thenReturn(true);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.approve(followDto);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }

    @Test
    public void test_approve_non_pending_request_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";

        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.PENDING.getCode()
        )).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.approve(followDto);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:PENDING status not found for %s following %s",
                        followerId,
                        followingId
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_reject_pending_request_should_update_follower_to_rejected() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.SEMI.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.PENDING.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId
        )).thenReturn(true);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.reject(followDto);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.REJECTED.getCode(), follow.getStatus());
    }

    @Test
    public void test_reject_non_pending_request_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";

        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.PENDING.getCode()
        )).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.reject(followDto);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:PENDING status not found for %s following %s",
                        followerId,
                        followingId
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_remove_follower_should_update_follower_to_unfollow() {
        String followerId = "followerId";
        String followingId = "followingId";
        User follower = new User()
                .setUserId(followerId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(follower);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId
        )).thenReturn(true);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);
        followService.remove(followDto);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.UNFOLLOW.getCode(), follow.getStatus());
    }

    @Test
    public void test_remove_non_approved_follower_should_failed() {
        String followerId = "followerId";
        String followingId = "followingId";

        Mockito.when(followRepository.isFollowing(
                followerId,
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(false);

        FollowDto followDto = new FollowDto()
                .setFollowerId(followerId)
                .setFollowingId(followingId);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.remove(followDto);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:REMOVE FAILED! %s is not following %s",
                        followerId,
                        followingId
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_approve_all_pending_request_should_success() {
        String followingId = "followingId";
        List<String> pendList = List.of("userA", "userB", "userC");
        Mockito.when(followRepository.queryPendingIdList(followingId)).thenReturn(pendList);

        FollowDto followDto = new FollowDto()
                .setFollowingId(followingId);
        followService.approveAll(followDto);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }

    @Test
    public void test_approve_all_empty_pending_request_should_failed() {
        String followingId = "followingId";
        Mockito.when(followRepository.queryPendingIdList(followingId)).thenReturn(null);

        FollowDto followDto = new FollowDto().setFollowingId(followingId);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.approveAll(followDto);
        });
        assertEquals(
                String.format("Follow not existed.:nothing to approve for user %s", followingId),
                expectedException.getErrorMsg()
        );
    }
}

