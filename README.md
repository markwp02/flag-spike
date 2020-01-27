# flag-spike
When transferring files to be processed by another program, there is a problem when the files are processed before they have finished transferring. The solution I am testing is to use a flag file to signal that the files are ready to be processed.

My program will have an inbox and outbox. Within the inbox, will be subdirectories with text files in them. When processed the entire sub directory will be moved to the outbox. However, only subdirectories with a flag file will be processed and moved. Subdirectories without a flag file will be skipped.

For another program that does the transferring, it would be simple enough to create a flag file as the last step before finishing.
