document.addEventListener('DOMContentLoaded', function() {
    var videoPlayer = document.getElementById('videoPlayer');
    var videoSource = document.getElementById('videoSource');
    var videoList = document.getElementById('videoList');

    videoList.addEventListener('click', function(e) {
        if (e.target.tagName === 'A') {
            e.preventDefault(); // 阻止链接默认行为
            var src = e.target.getAttribute('data-src');
            videoSource.src = src;
            videoPlayer.load(); // 重新加载视频
            videoPlayer.play(); // 播放视频
            // 移除其他链接的激活状态
            videoList.querySelectorAll('a').forEach(function(link) {
                link.classList.remove('active');
            });
            // 给当前链接添加激活状态
            e.target.classList.add('active');
        }
    });
});

