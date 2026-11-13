package com.hospital.medical.common;

/**
 * 校验分组：新增时要求必填字段齐全，修改时只校验格式，允许部分更新。
 */
public interface ValidGroups {

    /** 新增场景 */
    interface Create {
    }

    /** 修改场景 */
    interface Update {
    }
}
