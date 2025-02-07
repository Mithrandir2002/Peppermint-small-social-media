package org.peppermint.socialmedia.repository;

import org.peppermint.socialmedia.model.Chat;
import org.peppermint.socialmedia.model.Message;
import org.peppermint.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    public List<Chat> findByUsersId(Integer userId);
    @Query("select c from Chat c where :user MEMBER of c.users AND :reqUser MEMBER of c.users")
    public Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.messages m WHERE c.id = :id")
    Optional<Chat> findChatWithMessagesById(@Param("id") Integer id);

//    @Query(value = "SELECT * FROM chat c LEFT JOIN message m ON c.id = m.chat_id WHERE c.id = :id", nativeQuery = true)
//    Optional<Chat> findChatWithMessagesById(@Param("id") Integer id);
}
