package net.musicalWorld.service.impl;

import net.musicalWorld.model.News;
import net.musicalWorld.repository.NewsRepository;
import net.musicalWorld.service.NewsService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileUtil fileUtil;

    @Transactional(rollbackFor = Exception.class)
    public void add(News news, MultipartFile image) {
        news = newsRepository.save(news);
        String imgUrl = System.currentTimeMillis() + image.getOriginalFilename();
        news.setImgUrl(news.getId() + "/" + imgUrl);
        try {
            fileUtil.saveImage("news/" + news.getId(),imgUrl,image.getBytes());
            LOGGER.debug("news saved");
        }catch (Exception e){
            fileUtil.deleteImg("news/" + news.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<News> getAll(Pageable pageable) {
        return newsRepository.findAll(
                PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by(Sort.Direction.DESC,"createdDate"))).getContent();
    }

    @Override
    public int count() {
        return (int) newsRepository.count();
    }

    @Transactional
    public void deleteById(int id) {
        newsRepository.deleteById(id);
        fileUtil.deleteImg("news\\" + id);
        LOGGER.debug("news saved");
    }
}
