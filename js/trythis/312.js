// const getPosts = async (userId) => {
//   try {
//     // 게시글 목록 가져오기
//     // 여기는 'await'를 써서 데이터를 다 받을 때까지 기다립니다. (순차 처리)
//     const postsResponse = await fetch(`https://jsonplaceholder.typicode.com/posts?userId=${userId}`);
//     const posts = await postsResponse.json();

const { getPositivePatterns } = require("fast-glob/out/managers/tasks");

//     // 댓글 목록 가져오기
//     // Promise.all을 사용해 '한방에' 실행합니다. (병렬 처리)
//     const postsWithComments = await Promise.all(
//       posts.map(async (post) => {
//         // 각 게시글에 맞는 댓글 요청
//         const commentsResponse = await fetch(`https://jsonplaceholder.typicode.com/posts/${post.id}/comments`);
//         const comments = await commentsResponse.json();

//         // 게시글 + 댓글 합치기
//         return {
//           postId: post.id,
//           title: post.title,
//           comments: comments,
//         };
//       })
//     );

//     return postsWithComments;

//   } catch (error) {
//     console.error(error);
//   }
// };

// // 실행
// getPosts(1).then(data => console.log(data));
const API = 'https://jsonplaceholder.typicode.com/posts?userId=1';
const getPostsByUserId = async userId => fetch(`${API}/posts?userId=${userId}`).then(res => res.json()); // 여기까지 해야 promise
const getCommentsByPostId = async postId => fetch(`${API}/posts/${postId}/comments`).then(res => res.json()); // 여기까지 해야 promise

async function fetchData(){
    const posts = await getPostsByUserId(1);
    console.log("🚀 ~ posts:", posts)

    const postComments = await Promise.all( // 코멘트 들은 all 로 한번에 읽자.
        posts.map(post => getCommentsByPostId(post.id))
    );
    const results = [];
    for(let i = 0; i<posts.length; i++){
        const {id: postId, title} = posts[i];
        const comments = postComments[i].map(({id, email, body}) => ({id, email, body}));
        results.push({postId, title, comments})
    }

    console.log('results : ', )
}



