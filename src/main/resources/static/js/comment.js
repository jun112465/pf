let commentInputBtns = document.getElementsByClassName("comment-input-btn");
let commentInput = document.getElementsByClassName("comment-input");
let postIdList = document.getElementsByClassName("postIdList");
for(let i=0; i<commentInput.length; i++){
    let url = new URL(window.location.href);
    let categoryId = url.searchParams.get("categoryId")
    let pageNo = url.searchParams.get("pageNo")
    commentInputBtns[i].addEventListener('click', ()=>{
        console.log("add comment, categoryId : " + categoryId);
        fetch("/comment/add", {
            method : "POST",
            headers: { 'Content-Type': 'application/json' },
            body : JSON.stringify({
                'postId' : postIdList[i].value,
                'content' : commentInput[i].value,
                'pageNo' : pageNo,
                'pageCategory' : categoryId
            })
        })
            .then(response=>response.text())
            // .then(data => console.log(data));
            .then(data => location.href = location.origin + "/post/get" + data)
    })
}