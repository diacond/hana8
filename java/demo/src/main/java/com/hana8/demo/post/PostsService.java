package com.hana8.demo.post;

import com.hana8.demo.dto.PostDTO;
import com.hana8.demo.dto.ReplyDTO;
import com.hana8.demo.entity.Reply;
import java.util.List;

public interface PostsService {
	public List<Posts> getList(boolean isList);

	public Posts getPost(Long id, boolean isList);

	public Posts addPost(PostsDTO post, boolean isList);

	public Posts editPost(PostsDTO post, boolean isList);

	public int removePost(Long id, boolean isList);

	public List<ReplyDTO> getReplies(Long postId){
		List<Reply> replies = replyRepository.findA
	}
}
