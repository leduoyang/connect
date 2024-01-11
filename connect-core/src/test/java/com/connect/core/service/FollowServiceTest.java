package com.connect.core.service;

import com.connect.api.common.RequestMetaInfo;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.follow.impl.FollowServiceImpl;
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
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollower = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followingName = "following";
        long followingId = 2L;
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(following);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollower);
        Mockito.when(followRepository.isFollowing(requestMetaInfo.getUserId(), followingId)).thenReturn(false);

        followService.follow(followingName, requestMetaInfo);

        verify(followRepository, times(1)).createFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }

    @Test
    public void test_follow_semi_user_should_pending() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollower = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followingName = "following";
        long followingId = 2L;
        User mockFollowing = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.SEMI.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(mockFollowing);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(mockFollowing);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollower);
        Mockito.when(followRepository.isFollowing(requestMetaInfo.getUserId(), followingId)).thenReturn(false);

        followService.follow(followingName, requestMetaInfo);

        verify(followRepository, times(1)).createFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.PENDING.getCode(), follow.getStatus());
    }

    @Test
    public void test_follow_private_user_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        String followingName = "following";
        long followingId = 2L;
        User follower = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PRIVATE.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(following);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(following);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(follower);
        Mockito.when(followRepository.isFollowing(requestMetaInfo.getUserId(), followingId)).thenReturn(false);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.follow(followingName, requestMetaInfo);
        });
        assertEquals(
                "Not permitted for this request:target user not available for following: " + followingName,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_follow_deleted_user_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        String followingName = "following";
        long followingId = 2L;
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(following);
        Mockito.when(followRepository.isFollowing(requestMetaInfo.getUserId(), followingId)).thenReturn(false);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.follow(followingName, requestMetaInfo);
        });
        assertEquals(
                "Not permitted for this request:target user not available for following: " + followingName,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_unfollow_following_user_should_unfollow() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollower = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followingName = "following";
        long followingId = 2L;
        User mockFollowing = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(mockFollowing);
        Mockito.when(userRepository.internalQueryUserByUserId(followingId)).thenReturn(mockFollowing);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollower);
        Mockito.when(followRepository.isFollowing(
                requestMetaInfo.getUserId(),
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                requestMetaInfo.getUserId(),
                followingId
        )).thenReturn(true);

        followService.unfollow(followingName, requestMetaInfo);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.UNFOLLOW.getCode(), follow.getStatus());
    }

    @Test
    public void test_unfollow_not_following_user_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        String followingName = "following";
        long followingId = 2L;
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(following);

        Mockito.when(followRepository.isFollowing(
                requestMetaInfo.getUserId(),
                followingId,
                FollowStatus.APPROVED.getCode()
        )).thenReturn(false);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.unfollow(followingName, requestMetaInfo);
        });
        assertEquals(
                "Follow not existed.:UNFOLLOW FAILED! you are not following " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_unfollow_non_existed_user_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        String followingName = "following";
        long followingId = 2L;
        User following = new User()
                .setUserId(followingId)
                .setStatus(UserStatus.DELETED.getCode())
                .setVersion(1);

        Mockito.when(userRepository.internalQueryUserByUsername(followingName)).thenReturn(following);
        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.unfollow(followingName, requestMetaInfo);
        });
        assertEquals(
                "Follow not existed.:UNFOLLOW FAILED! you are not following " + followingId,
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_approve_pending_request_should_update_follower_to_approved() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.PENDING.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId()
        )).thenReturn(true);

        followService.approve(followerName, requestMetaInfo);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }

    @Test
    public void test_approve_non_pending_request_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.PENDING.getCode()
        )).thenReturn(false);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.approve(followerName, requestMetaInfo);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:PENDING status not found for %s following %s",
                        followerId,
                        requestMetaInfo.getUserId()
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_reject_pending_request_should_update_follower_to_rejected() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.PENDING.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId()
        )).thenReturn(true);

        followService.reject(followerName, requestMetaInfo);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.REJECTED.getCode(), follow.getStatus());
    }

    @Test
    public void test_reject_non_pending_request_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.PENDING.getCode()
        )).thenReturn(false);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.reject(followerName, requestMetaInfo);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:PENDING status not found for %s following %s",
                        followerId,
                        FollowStatus.PENDING.getCode()
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_remove_follower_should_update_follower_to_unfollow() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(followerId)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.APPROVED.getCode()
        )).thenReturn(true);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId()
        )).thenReturn(true);

        followService.remove(followerName, requestMetaInfo);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.UNFOLLOW.getCode(), follow.getStatus());
    }

    @Test
    public void test_remove_non_approved_follower_should_failed() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        User mockFollowing = new User()
                .setUserId(requestMetaInfo.getUserId())
                .setVersion(1);

        String followerName = "follower";
        long followerId = 2L;
        User mockFollower = new User()
                .setUserId(followerId)
                .setUsername(followerName)
                .setStatus(UserStatus.PUBLIC.getCode())
                .setVersion(1);
        Mockito.when(userRepository.internalQueryUserByUsername(followerName)).thenReturn(mockFollower);
        Mockito.when(userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId())).thenReturn(mockFollowing);
        Mockito.when(followRepository.isFollowing(
                followerId,
                requestMetaInfo.getUserId(),
                FollowStatus.APPROVED.getCode()
        )).thenReturn(false);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            followService.remove(followerName, requestMetaInfo);
        });
        assertEquals(
                String.format(
                        "Follow not existed.:REMOVE FAILED! %s is not following %s",
                        followerId,
                        requestMetaInfo.getUserId()
                ),
                expectedException.getErrorMsg()
        );
    }

    @Test
    public void test_approve_all_pending_request_should_success() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        List<Long> pendList = List.of(2L, 3L, 4L);
        Mockito.when(followRepository.queryPendingIdList(requestMetaInfo.getUserId())).thenReturn(pendList);
        followService.approveAll(requestMetaInfo);

        verify(followRepository, times(1)).updateFollow(captor.capture());
        Follow follow = captor.getValue();
        assertEquals(FollowStatus.APPROVED.getCode(), follow.getStatus());
    }


    /**
     * Update: followService.approveAll no longer check if pending list is empty
     */
//    @Test
//    public void test_approve_all_empty_pending_request_should_failed() {
//        long followingId = 2L;
//        Mockito.when(followRepository.queryPendingIdList(followingId)).thenReturn(null);
//
//        FollowDto followDto = new FollowDto().setFollowingId(followingId);
//        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
//            followService.approveAll(followDto);
//        });
//        assertEquals(
//                String.format("Follow not existed.:nothing to approve for user %s", followingId),
//                expectedException.getErrorMsg()
//        );
//    }
}

