(()=>{
    let areaItems = document.getElementsByClassName("area-item")
    let allPosts = areaItems[0];
    let userPosts = areaItems[1];
    let allPostBox = document.getElementById("allPostList");
    let userPostBox = document.getElementById("userPostList");

    if(userPosts.id = 'selected')
        allPostBox.style.display = 'none';

    allPosts.addEventListener('click', ()=>{

        // 선택된 areaItem 에 밑줄이 생기도록 하는 기능
        allPosts.id = 'selected';
        userPosts.id = 'unselected';

        // 선택된 areaItem 에 해당하는 postList 보여주기
        allPostBox.style.display = "flex";
        userPostBox.style.display = "none";
    })

    userPosts.addEventListener('click', ()=>{

        // 선택된 areaItem 에 밑줄이 생기도록 하는 기능
        userPosts.id = 'selected';
        allPosts.id = 'unselected';

        // 선택된 areaItem 에 해당하는 postList 보여주기
        allPostBox.style.display = "none";
        userPostBox.style.display = "flex";
    })


})()