alert("Hi");
import { uploadFile } from "@uploadcare/upload-client";

const data = new Blob(["This is example file"], { type: "text/plain" });
alert(data);
const file = await uploadFile(data, {
    publicKey: "YOUR_PUBLIC_KEY",
    fileName: "example.txt",
});

console.log(file);