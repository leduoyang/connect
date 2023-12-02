package connect.data.repository;

import com.connect.data.entity.Comment;
import com.connect.data.repository.ICommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestDBApplication.class)
public class ICommentRepositoryTest {
    @Autowired
    private ICommentRepository commentRepository;

    @Test
    void Get_Comment_By_Comment_Id_Should_Return_Target_Comment() {
        Long commentId = 1L;
        Comment comment = commentRepository.queryCommentById(commentId);
        System.out.println(comment.toString());
    }
}
