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


    // get categories and set selection
    fetch('/category?' + new URLSearchParams({
        userId : document.getElementById("userId").value
    }))
        .then(response => {
            // 응답이 성공적으로 돌아오면 JSON 형식으로 파싱
            return response.json();
        })
        .then(data => {
            // JSON 데이터를 사용하여 무엇인가를 수행
            const selectElement = document.getElementById('categories');
            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name; // 옵션의 값을 설정
                option.text = item.name; // 옵션의 텍스트 설정
                selectElement.appendChild(option);
            });
            console.log(data);
        })
        .catch(error => {
            // 오류 처리
            console.error('오류 발생:', error);
        });

})()