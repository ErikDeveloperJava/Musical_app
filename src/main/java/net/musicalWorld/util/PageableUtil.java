package net.musicalWorld.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {

    public static Pageable getChecked(Pageable pageable,int length){
        if(pageable.getPageNumber() >= length){
            return PageRequest.of(length - 1,pageable.getPageSize());
        }else {
            return pageable;
        }
    }

    public static int getLength(int count,int pageSize){
        int length;
        if(count % pageSize != 0 ){
            length = (count/pageSize) + 1;
        }else {
            length = count/pageSize;
        }
        return length;
    }
}
