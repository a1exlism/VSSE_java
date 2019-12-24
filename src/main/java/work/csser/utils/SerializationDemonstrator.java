package work.csser.utils;


import static java.lang.System.out;

import java.io.*;

/**
 * @author Dustin
 * @className SerializationDemonstrator
 * @description Simple serialization/deserialization demonstrator.
 * Working in workspace user.dir
 *
 * @link http://marxsoftware.blogspot.com/2014/02/serializing-java-objects-with-non.html
 * @link2 https://stackoverflow.com/questions/9620683/java-fileoutputstream-create-file-if-not-exists
 */

public class SerializationDemonstrator {
  final static private String workspace = System.getProperty("user.dir");

  /**
   * Serialize the provided object to the file of the provided name.
   *
   * @param objectToSerialize Object that is to be serialized to file; it is
   *                          best that this object have an individually overridden toString()
   *                          implementation as that is used by this method for writing our status.
   * @param relativePath      parent path for filename(in workspace)
   * @param fileName          Name of file to which object is to be serialized.
   * @throws IllegalArgumentException Thrown if either provided parameter is null.
   */
  public static <T> void serialize(final T objectToSerialize, final String relativePath, final String fileName) {

    if (objectToSerialize == null) {
      throw new IllegalArgumentException("Object to be serialized cannot be null.");
    }

    //  create parent directories if not exists
    String pPath = workspace + File.separator + relativePath;
    new File(pPath).mkdirs();
    //  create file and write object
    try (FileOutputStream fos = new FileOutputStream(pPath + File.separator + fileName);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(objectToSerialize);
//      out.println("=== Serialization of Object === Demonstrator ===\n" + objectToSerialize + " completed.");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  /**
   * Provides an object deserialized from the file indicated by the provided
   * file name.
   *
   * @param <T>               Type of object to be deserialized.
   * @param relativePath      parent path for filename(in workspace)
   * @param fileToDeserialize Name of file from which object is to be deserialized.
   * @return Object deserialized from provided filename as an instance of the
   * provided class; may be null if something goes wrong with deserialization.
   * @throws IllegalArgumentException Thrown if either provided parameter is null.
   */
  public static <T> T deserialize(final String relativePath, final String fileToDeserialize) {
    //  check file existence
    File file = new File(workspace + File.separator + relativePath + File.separator + fileToDeserialize);

    if (!file.isFile()) {
      throw new IllegalArgumentException("Cannot deserialize from a null filename.");
    }

    T objectOut = null;
    try (FileInputStream fis = new FileInputStream(file);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
      objectOut = (T) ois.readObject();
//      out.println("=== Deserialization of Object === Demonstrator===\n" + objectOut + " is completed.");
    } catch (IOException | ClassNotFoundException exception) {
      exception.printStackTrace();
    }
    return objectOut;
  }
}
