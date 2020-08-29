package cc.buddies.cleanarch.data.db.converter;

import androidx.room.TypeConverter;

import java.util.List;

import cc.buddies.cleanarch.data.serialize.JSONUtils;

public class ListStringConverter {

    @TypeConverter
    public static String fromListString(List<String> list) {
        return JSONUtils.toJSON(list);
    }

    @TypeConverter
    public static List<String> toListString(String data) {
        return JSONUtils.toList(data, String.class);
    }

}
