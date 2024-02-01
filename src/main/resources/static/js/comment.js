let commentInputBtns = document.getElementsByClassName("comment-input-btn");
let commentInput = document.getElementsByClassName("comment-input");
let postIdList = document.getElementsByClassName("postIdList");
for(let i=0; i<commentInput.length; i++){
    commentInputBtns[i].addEventListener('click', ()=>{
        fetch("/comment/add", {
            method : "POST",
            headers: { 'Content-Type': 'application/json' },
            body : JSON.stringify({
                'postId' : postIdList[i].value,
                'content' : commentInput[i].value
            })
        }).then(data => location.href='/')
    })
}