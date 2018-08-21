package net.musicalWorld.controller;

import net.musicalWorld.form.AlbumRequestForm;
import net.musicalWorld.model.Album;
import net.musicalWorld.model.Musician;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.AlbumService;
import net.musicalWorld.util.FileUtil;
import net.musicalWorld.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AlbumController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AlbumService albumService;

    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/admin/album/add")
    public String addGet(Model model){
        model.addAttribute("form",new AlbumRequestForm());
        return ALBUM_ADD;
    }

    @PostMapping("/admin/albums")
    public @ResponseBody
    List<Album> loadAlbums(){
        return albumService.getAll();
    }

    @PostMapping("/admin/album/add")
    public String addPost(@ModelAttribute("form")@Valid AlbumRequestForm form, BindingResult result){
        LOGGER.debug("form : {}",form);
        if(result.hasErrors()){
            return ALBUM_ADD;
        }else if(form.getImage().isEmpty() || !fileUtil.isValidImgFormat(form.getImage().getContentType())){
            result.addError(new FieldError("form","image",""));
            return ALBUM_ADD;
        }else {
            Album album = Album.builder()
                    .name(form.getName())
                    .description(form.getDescription())
                    .releaseDate(form.getReleaseDate())
                    .imgUrl("")
                    .musician(Musician.builder().id(form.getMusicianId()).build())
                    .build();
            albumService.add(album,form.getImage());
            return "redirect:/albums";
        }
    }

    @GetMapping("/albums")
    public String albums(Pageable pageable, Model model,
                         @RequestParam(value = "token",required = false,defaultValue = "NONE")String token){
        int count = albumService.count();
        int length = PageableUtil.getLength(count,6);
        pageable = PageableUtil.getChecked(pageable,length);
        model.addAttribute("albums",albumService.getAll(pageable));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length);
        return token.equals("NONE") ? ALBUMS : ALBUMS_JS;
    }


    @GetMapping("/album/search")
    public String albumSearch(Pageable pageable, Model model,
                         @RequestParam("name")String name){
        int count = albumService.countByNameContains(name);
        int length = PageableUtil.getLength(count,6);
        pageable = PageableUtil.getChecked(pageable,length);
        model.addAttribute("albums",albumService.getAllByNameContains(name,pageable));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length);
        model.addAttribute("name",name);
        return ALBUM_SEARCH;
    }

    @PostMapping("/admin/album/delete/{id}")
    public @ResponseBody
    boolean delete(@PathVariable("id")int id){
        LOGGER.debug("albumId : {}",id);
        albumService.deleteById(id);
        return true;
    }

    @GetMapping("/album/{id}")
    public String oneAlbum(@PathVariable("id")String strId,Model model){
        LOGGER.debug("albumId : {}",strId);
        int id;
        try {
            if(!albumService.existsById((id = Integer.parseInt(strId)))){
                return "redirect:/albums";
            }
            model.addAttribute("album",albumService.getDetailById(id));
        }catch (NumberFormatException e){
            return "redirect:/albums";
        }
        return ALBUM_DETAIL;
    }
}
