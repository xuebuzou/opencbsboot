package cn.com.bocd.opencbsboot.util.toolkit;

import java.util.List;

public interface Pageable<T> {
    int total();
    int total(int start, int end);
    List<T> slice(int start, int end);
}
