function getWeekName(date){
    if(date === undefined) date = new Date();
    console.log("🚀 ~ getWeekName ~ date:", date)

    let weekName;
    switch(date.getDay()){
        case 0:
            weekName = '일';
            break;
        case 1:
            weekName = '월';
            break;
        case 2:
            weekName = '화';
            break;
        case 3:
            weekName = '수';
            break;
        case 4:
            weekName = '목';
            break;
        case 5:
            weekName = '금';
            break;
        case 6:
            weekName = '토'; 
            break;
    }
    
}
/*getWeekName(new Date()); // 얘는 잘 나옴 인자 줬으니...
getWeekName(); // undefined로 나옴->if문으로 붙여준다.

date = date ?? new Date();
console.log("🚀 ~ date:", date)
*/
