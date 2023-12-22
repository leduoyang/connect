//package connect.data.repository;
//
//import com.connect.data.entity.Comment;
//import com.connect.data.repository.ICommentRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest(classes = TestDBApplication.class)
//public class ICommentRepositoryTest {
//    @Autowired
//    private ICommentRepository commentRepository;
//
//    @Test
//    @Disabled
//    void get_comment_by_comment_id_should_return_target_comment() {
//        Long commentId = 1L;
//        Comment comment = commentRepository.queryCommentById(commentId, "ROOT");
//        System.out.println(comment.toString());
//    }
//}
