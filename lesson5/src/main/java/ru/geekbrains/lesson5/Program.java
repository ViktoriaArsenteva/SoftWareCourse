package ru.geekbrains.lesson5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class Program {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UILayer editor3D = new Editor3D();

        boolean f = true;
        while (f) {
            System.out.println();
            System.out.println("*** Мой 3D редактор ***");
            System.out.println("***********************");
            System.out.println("1. Открыть проект.");
            System.out.println("2. Созранить проект.");
            System.out.println("3. Отобразить параметры проекта.");
            System.out.println("4. Отобразить все модели проекта.");
            System.out.println("5. Отобразить все текстуры проекта.");
            System.out.println("6. Выполнить рендер всех моделей.");
            System.out.println("7. Выполнить рендер модели.");
            System.out.println("8. Добавить новую модель.");
            System.out.println("9. Удалить модель.");
            System.out.println("0. ЗАВЕРШЕНИ РАБОТЫ ПРИЛОЖЕНИЯ.");
            System.out.println("Пожалуйста, выберите пункт меню.");
            if (scanner.hasNextInt()) {
                int no = scanner.nextInt();
                scanner.nextLine();
                try {
                    switch (no) {
                        case 0:
                            System.out.println("Завершение работы приложения.");
                            f = false;
                            break;
                        case 1:
                            System.out.println("Укажите наименование файла проекта: ");
                            String fileName = scanner.nextLine();
                            editor3D.openProject(fileName);
                            System.out.println("Проект успешно открыт.");
                            break;
                        case 2:
                            System.out.println("Проект успешно сохранен.");
                            editor3D.saveProject();
                            break;
                        case 3:
                            editor3D.showProjectSetting();
                            break;
                        case 4:
                            editor3D.printAllModels();
                            break;
                        case 5:
                            editor3D.printAllTextures();
                            break;
                        case 6:
                            editor3D.renderAll();
                            break;
                        case 7:
                            System.out.println("Укажите номер модели: ");
                            if (scanner.hasNext()) {
                                int modelNo = scanner.nextInt();
                                scanner.nextLine();
                                editor3D.renderModel(modelNo);
                            } else {
                                System.out.println("Номер модели указан некорректно.");
                            }
                            break;
                        case 8:
                            editor3D.addModel();
                            break;
                        case 9:
                            System.out.println("Укажите номер модели: ");
                            if (scanner.hasNext()) {
                                int modelNo = scanner.nextInt();
                                scanner.nextLine();
                                editor3D.removeModel(modelNo);
                            } else {
                                System.out.println("Номер модели указан некорректно.");
                            }
                            break;
                        default:
                            System.out.println("Укажите корректный пункт меню.");
                            scanner.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Укажите корректный пункт меню.");
                scanner.nextLine();
            }

        }
    }

}

/**
 * UI (User Interface)
 */
class Editor3D implements UILayer {

    private ProjectFile projectFile;
    private BusinessLogicalLayer businessLogicalLayer;

    private DatabaseAccess databaseAccess;
    private Database database;

    public BusinessLogicalLayer getBusinessLogicalLayer() {
        return businessLogicalLayer;
    }

    private void initialize(){
        database = new EditorDatabase(projectFile);
        databaseAccess = new EditorDatabaseAccess(database);
        businessLogicalLayer = new EditorBusinessLogicalLayer(databaseAccess);
    }

    @Override
    public void openProject(String fileName) {
        this.projectFile = new ProjectFile(fileName);
        initialize();
    }

    @Override
    public void showProjectSetting() {
        // Предусловие
        checkProjectFile();

        System.out.println("*** Project v1 ***");
        System.out.println("**************");
        System.out.printf("fileName: %s\n", projectFile.getFileName());
        System.out.printf("setting1: %d\n", projectFile.getSetting1());
        System.out.printf("setting2: %s\n", projectFile.getSetting2());
        System.out.printf("setting3: %s\n", projectFile.getSetting3());
        System.out.println("**************");
    }

    private void checkProjectFile() {
        if (projectFile == null) {
            throw new RuntimeException("Файл проекта не определен");
        }
    }

    @Override
    public void saveProject() {
        database.save();
        System.out.println("Изменения успешно сохранены.");
    }

    @Override
    public void printAllModels() {
        // Предусловие
        checkProjectFile();

        ArrayList<Model3D> models = (ArrayList<Model3D>) businessLogicalLayer.
                getAllModels();
        for (int i = 0; i < models.size(); i++) {
            System.out.printf("===%d===\n", i + 1);
            System.out.println(models.get(i));
            for (Texture texture: models.get(i).getTextures()) {
                System.out.printf("\t%s\n", texture);
            }
        }
    }

    @Override
    public void printAllTextures() {
        // Предусловие
        checkProjectFile();

        ArrayList<Texture> textures = (ArrayList<Texture>) businessLogicalLayer.
                getAllTextures();
        for (int i = 0; i < textures.size(); i++) {
            System.out.printf("===%d===\n", i + 1);
            System.out.println(textures.get(i));
        }
    }

    @Override
    public void renderAll() {
        // Предусловие
        checkProjectFile();

        System.out.println("Подождите...");
        long startTime = System.currentTimeMillis();
        businessLogicalLayer.renderAllModels();
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.printf("Операция выполнена за %d мс", endTime);
    }

    @Override
    public void renderModel(int i) {
        // Предусловие
        checkProjectFile();

        ArrayList<Model3D> models = (ArrayList<Model3D>) businessLogicalLayer.
                getAllModels();
        if (i < 1 || i > models.size()) {
            throw new RuntimeException("Номер модели указан не корректно");
        }
        System.out.println("Подождите...");
        long startTime = System.currentTimeMillis();
        businessLogicalLayer.renderModel(models.get(i));
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.printf("Операция выполнена за %d мс", endTime);
    }

    @Override
    public void addModel() {
        // Предусловие
        checkProjectFile();

        System.out.println("Модель успешно добавлена.");
        database.CreateModel3DElement();
    }

    @Override
    public void removeModel(int modelNo) {
        // Предусловие
        checkProjectFile();

        ArrayList<Model3D> models = (ArrayList<Model3D>) businessLogicalLayer.
                getAllModels();
        if (modelNo < 1 || modelNo > models.size()) {
            throw new RuntimeException("Номер модели указан не корректно");
        }
        System.out.println("Подождите...");
        long startTime = System.currentTimeMillis();
        database.removeModel3DElement(modelNo);
        long endTime = (System.currentTimeMillis() - startTime);
        System.out.println("Операция удаления выполнена успешно.");
        System.out.printf("Операция выполнена за %d мс", endTime);

    }

}

/**
 * Интерфейс UI
 */
interface UILayer {

    void openProject(String fileName);
    void showProjectSetting();
    void saveProject();
    void printAllModels();
    void printAllTextures();
    void renderAll();
    void renderModel(int i);
    void addModel();
    void removeModel(int modelNo);
}

/**
 * Реализация Business Logical Layer
 */
class EditorBusinessLogicalLayer implements BusinessLogicalLayer {

    private DatabaseAccess databaseAccess;

    public EditorBusinessLogicalLayer(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public Collection<Texture> getAllTextures() {
        return databaseAccess.getAllTextures();
    }

    @Override
    public Collection<Model3D> getAllModels() {
        return databaseAccess.getAllModels();
    }

    @Override
    public void renderModel(Model3D model) {
        processRender(model);
    }

    @Override
    public void renderAllModels() {
        for (Model3D model: databaseAccess.getAllModels()) {
            processRender(model);
        }
    }

    Random random = new Random();

    private void processRender(Model3D model) {
        try {
            Thread.sleep(2500 - random.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Интерфейс BLL (Business Logical Layer)
 */
interface BusinessLogicalLayer {

    Collection<Texture> getAllTextures();

    Collection<Model3D> getAllModels();

    void renderModel(Model3D model);

    void renderAllModels();
}

/**
 * Имплементация DAC
 */
class EditorDatabaseAccess implements DatabaseAccess {

    private final Database editorDatabase;

    public EditorDatabaseAccess(Database editorDatabase) {
        this.editorDatabase = editorDatabase;
    }

    @Override
    public void addEntity(Entity entity) {
        editorDatabase.getAll().add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        editorDatabase.getAll().remove(entity);
    }

    @Override
    public Collection<Texture> getAllTextures() {
        Collection<Texture> textures = new ArrayList<>();
        for (Entity entity: editorDatabase.getAll()) {
            if (entity instanceof Texture) {
                textures.add((Texture) entity);
            }
        }
        return textures;
    }

    @Override
    public Collection<Model3D> getAllModels() {
        Collection<Model3D> models = new ArrayList<>();
        for (Entity entity: editorDatabase.getAll()) {
            if (entity instanceof Model3D) {
                models.add((Model3D)entity);
            }
        }
        return models;
    }
}

/**
 * Интерфейс DAC
 */
interface DatabaseAccess {
    void addEntity(Entity entity);
    void removeEntity(Entity entity);
    Collection<Texture> getAllTextures();
    Collection<Model3D> getAllModels();
}

/**
 * Database
 */
class EditorDatabase implements Database{

    private Random random = new Random();
    private final ProjectFile projectFile;
    private Collection<Entity> entities;

    public EditorDatabase(ProjectFile projectFile) {
        this.projectFile = projectFile;
        load();
    }

    @Override
    public void load() {
        //TODO: загрузка всех сущностей проекта (модели, текстуры и т. д.)
    }

    @Override
    public void save() {
        //TODO: сохранение текущего состояния всех сущностей проекта
    }

    public Collection<Entity> getAll() {
        if (entities == null) {
            entities = new ArrayList<>();
            int entCount = random.nextInt(5, 11);
            for (int i = 0; i < entCount; i++) {
                generateModelAndTextures();
            }
        }
        return entities;
    }

    private void generateModelAndTextures() {
        Model3D model3D = new Model3D();
        int txCount = random.nextInt(3);
        for (int i = 0; i < txCount; i++) {
            Texture texture = new Texture();
            model3D.getTextures().add(texture);
            entities.add(texture);
        }
        entities.add(model3D);
    }

    @Override
    public Model3D CreateModel3DElement() {
        Model3D model3D = new Model3D();
        int txCount = random.nextInt(3);
        for (int i = 0; i < txCount; i++) {
            Texture texture = new Texture();
            model3D.getTextures().add(texture);
            entities.add(texture);
        }
        entities.add(model3D);
        return model3D;
    }

    @Override
    public void removeModel3DElement(int i) {
        Collection<Model3D> models = new ArrayList<>();
        for (Entity entity: entities) {
            if (entity instanceof Model3D){
                models.add((Model3D) entity);
                if (models.size() == i) {
                    entities.remove(entity);
                }
            }
        }
    }
}

/**
 * Интерфейс БД
 */
interface Database {
    void load();

    void save();

    Collection<Entity> getAll();

    Model3D CreateModel3DElement();
    void removeModel3DElement(int i);

}

/**
 * 3D модель
 */
class Model3D implements Entity {
    private static int counter = 10000;
    private int id;

    private Collection<Texture> textures = new ArrayList<>();

    {
        id = ++counter;
    }

    @Override
    public int getId() {
        return id;
    }

    public Collection<Texture> getTextures() {
        return textures;
    }

    public Model3D() {
    }

    public Model3D(Collection<Texture> textures) {
        this.textures = textures;
    }

    @Override
    public String toString() {
        return String.format("3DModel #%s", id);
    }
}

/**
 * Текстура
 */
class Texture implements Entity {
    private static int counter = 50000;
    private int id;

    {
        id = ++counter;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Texture #%s", id);
    }
}

/**
 * Сущность
 */
interface Entity {
    int getId();
}

/**
 * Файл проекта
 */
class ProjectFile {
    private String fileName;
    private int setting1;
    private String setting2;
    private boolean setting3;

    public ProjectFile(String fileName) {
        this.fileName = fileName;
        // TODO: Загрузка настроек проекта из файла
        setting1 = 1;
        setting2 = "...";
        setting3 = false;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSetting1() {
        return setting1;
    }

    public String getSetting2() {
        return setting2;
    }

    public boolean getSetting3() {
        return setting3;
    }
}