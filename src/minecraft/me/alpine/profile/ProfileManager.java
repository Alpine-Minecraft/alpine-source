package me.alpine.profile;

import com.google.gson.*;
import lombok.Getter;
import me.alpine.Alpine;
import me.alpine.mod.Mod;
import me.alpine.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ProfileManager {

    @Getter private static final ProfileManager instance = new ProfileManager();

    private final File lastProfileFile = new File(Alpine.getInstance().getDirectory(), "active-profile.dat");
    private final File profilesDirectory = new File(Alpine.getInstance().getDirectory(), "profiles");

    private ProfileManager() {
        if (profilesDirectory.mkdirs()) {
            Alpine.getInstance().getLogger().info("[Alpine] Created profiles directory");
        }

        try {
            if (lastProfileFile.createNewFile()) {
                save("default");
                Alpine.getInstance().getLogger().info("[Alpine] Created default profile");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String name) {
        saveToLastProfile(name);

        File profileFile = new File(profilesDirectory, name + ".json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject json = new JsonObject();

        /* Adds general info about the profile */
        {
            JsonObject info = new JsonObject();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());

            info.addProperty("name", name);
            info.addProperty("version", Alpine.getInstance().getVersion());
            info.addProperty("date", sdf.format(date));

            json.add("info", info);
        }

        for (Mod m: Alpine.getInstance().getModsManager().getMods()) {
            json.add(m.getName(), m.toJson());
        }

        try {
            Files.write(profileFile.toPath(), gson.toJson(json).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Alpine.getInstance().getLogger().error("[Alpine] Failed to save profile " + name, e);
        }

    }

    public void load(String name) {
        saveToLastProfile(name);

        File profileFile = new File(profilesDirectory, name + ".json");

        if (!profileFile.exists()) {
            Alpine.getInstance().getLogger().error("[Alpine] Failed to load profile " + name + " - file not found");
            return;
        }

        JsonObject json;
        try {
            json = new JsonParser().parse(new FileReader(profileFile)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            Alpine.getInstance().getLogger().error("[Alpine] Failed to load profile " + name, e);
            return;
        }

        for (Map.Entry<String, JsonElement> entry: json.entrySet()) {
            if (entry.getKey().equals("info")) {
                continue;
            }

            Mod m = Alpine.getInstance().getModsManager().getMod(entry.getKey());
            if (m != null) {
                m.fromJson(entry.getValue().getAsJsonObject());
            } else {
                Alpine.getInstance().getLogger().warn("[Alpine] Malformed JSON, mod " + entry.getKey() + " not found");
            }
        }
    }

    public void delete(String name) {

    }

    public void saveToLastProfile(String name) {
        try {
            Path path = lastProfileFile.toPath();
            Files.write(path, name.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLastProfile() {
        load(getlastProfile());
    }

    public String getlastProfile() {
        if (FileUtil.readLines(lastProfileFile).size() == 0) {
            return "default";
        } else {
            return FileUtil.readLines(lastProfileFile).get(0);
        }
    }
}
