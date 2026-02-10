package com.mediaplayer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayerController {
    
    @FXML private Label nowPlayingLabel;
    @FXML private Label artistLabel;
    @FXML private Label currentTimeLabel;
    @FXML private Label totalTimeLabel;
    @FXML private ProgressBar progressBar;
    
    @FXML private Button prevButton;
    @FXML private Button historyButton;
    @FXML private Button nextButton;
    
    @FXML private Slider volumeSlider;
    
    @FXML private ListView<String> historyListView;
    @FXML private ListView<String> playlistListView;
    
    // Media player components
    private MediaPlayer mediaPlayer;
    private List<Song> songList;
    private List<Song> playbackHistory;
    private ObservableList<String> historyItems;
    private ObservableList<String> playlistItems;
    
    private int currentSongIndex = 0;
    private boolean isPlaying = false;
    private Timeline timeline;
    
    // Song class to represent each track
    private class Song {
        String title;
        String artist;
        String filePath;
        Duration duration;
        
        Song(String title, String artist, String filePath) {
            this.title = title;
            this.artist = artist;
            this.filePath = filePath;
        }
        
        @Override
        public String toString() {
            return title + " • " + artist;
        }
    }
    
    public void initialize() {
        // Initialize data structures
        songList = new ArrayList<>();
        playbackHistory = new ArrayList<>();
        historyItems = FXCollections.observableArrayList();
        playlistItems = FXCollections.observableArrayList();
        
        // Set up list views
        historyListView.setItems(historyItems);
        playlistListView.setItems(playlistItems);
        
        // Initialize with demo data
        initializeDemoData();
        
        // Setup event handlers
        setupEventHandlers();
        
        // Setup timeline for progress updates
        setupTimeline();
        
        // Load first song
        loadSong(currentSongIndex);
        
        // Update UI
        updatePlaylistDisplay();
    }
    
    private void initializeDemoData() {
        // Add demo songs (in a real app, these would be loaded from files)
        songList.add(new Song("Chasing Dreams", "Aurora Sky", "demo_song_1.mp3"));
        songList.add(new Song("Ocean Waves", "Coastal Sound", "demo_song_2.mp3"));
        songList.add(new Song("Midnight City", "Neon", "demo_song_3.mp3"));
        songList.add(new Song("Starlight Road", "Horizon Lights", "demo_song_4.mp3"));
        songList.add(new Song("Lost Memories", "Unknown Artist", "demo_song_5.mp3"));
        songList.add(new Song("Sunset Drive", "Chill Vibes", "demo_song_6.mp3"));
        songList.add(new Song("Into the Night", "Lunar Echo", "demo_song_7.mp3"));
        songList.add(new Song("Morning Breeze", "Sunrise Melody", "demo_song_8.mp3"));
        
        // Set initial durations (demo values)
        songList.get(0).duration = Duration.seconds(252); // 04:12
        songList.get(1).duration = Duration.seconds(180);
        songList.get(2).duration = Duration.seconds(210);
        songList.get(3).duration = Duration.seconds(195);
        songList.get(4).duration = Duration.seconds(225);
        songList.get(5).duration = Duration.seconds(240);
        songList.get(6).duration = Duration.seconds(200);
        songList.get(7).duration = Duration.seconds(190);
        
        // Add to playback history (demo)
        addToHistory(songList.get(1)); // Ocean Waves
        addToHistory(songList.get(2)); // Midnight City
    }
    
    private void setupEventHandlers() {
        // Previous button
        prevButton.setOnAction(e -> playPrevious());
        
        // Next button
        nextButton.setOnAction(e -> playNext());
        
        // History button
        historyButton.setOnAction(e -> {
            // In a real app, this might toggle history view or show history controls
            System.out.println("History button clicked");
        });
        
        // Volume slider
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });
        
        // Progress bar click to seek
        progressBar.setOnMouseClicked(event -> {
            if (mediaPlayer != null) {
                double mouseX = event.getX();
                double progressBarWidth = progressBar.getWidth();
                double percentage = mouseX / progressBarWidth;
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(percentage));
            }
        });
        
        // History list selection
        historyListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = historyListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    playSongFromHistory(selected);
                }
            }
        });
        
        // Playlist list selection
        playlistListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = playlistListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    playSongFromPlaylist(selected);
                }
            }
        });
    }
    
    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> updateProgress()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    private void loadSong(int index) {
        if (index < 0 || index >= songList.size()) {
            return;
        }
        
        // Stop current playback
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            timeline.stop();
        }
        
        currentSongIndex = index;
        Song song = songList.get(index);
        
        // Update UI labels
        nowPlayingLabel.setText(song.title);
        artistLabel.setText(song.artist);
        
        // Update total time display
        if (song.duration != null) {
            totalTimeLabel.setText("/" + formatTime(song.duration));
        }
        
        // Add to history
        addToHistory(song);
        
        try {
            // In a real app, you would load actual media files
            // For demo purposes, we'll simulate playback
            
            // Reset progress
            progressBar.setProgress(0);
            currentTimeLabel.setText("00:00");
            
            // Start simulated playback
            isPlaying = true;
            timeline.play();
            
        } catch (Exception e) {
            System.err.println("Error loading song: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load song");
            alert.setContentText("Could not load: " + song.title);
            alert.showAndWait();
        }
    }
    
    private void playPrevious() {
        if (currentSongIndex > 0) {
            loadSong(currentSongIndex - 1);
        }
    }
    
    private void playNext() {
        if (currentSongIndex < songList.size() - 1) {
            loadSong(currentSongIndex + 1);
        }
    }
    
    private void addToHistory(Song song) {
        // Add to beginning of history
        playbackHistory.add(0, song);
        historyItems.add(0, song.toString());
        
        // Keep history limited to last 10 songs
        if (historyItems.size() > 10) {
            historyItems.remove(historyItems.size() - 1);
            playbackHistory.remove(playbackHistory.size() - 1);
        }
    }
    
    private void playSongFromHistory(String historyEntry) {
        for (int i = 0; i < playbackHistory.size(); i++) {
            Song song = playbackHistory.get(i);
            if (song.toString().equals(historyEntry)) {
                // Find the song in main list and play it
                for (int j = 0; j < songList.size(); j++) {
                    if (songList.get(j).title.equals(song.title) && 
                        songList.get(j).artist.equals(song.artist)) {
                        loadSong(j);
                        break;
                    }
                }
                break;
            }
        }
    }
    
    private void playSongFromPlaylist(String playlistEntry) {
        for (int i = 0; i < songList.size(); i++) {
            Song song = songList.get(i);
            if (song.toString().equals(playlistEntry)) {
                loadSong(i);
                break;
            }
        }
    }
    
    private void updateProgress() {
        if (!isPlaying) return;
        
        Song currentSong = songList.get(currentSongIndex);
        if (currentSong.duration != null) {
            // Simulate time progression (every 100ms = 0.1 seconds)
            // In a real app, this would use mediaPlayer.getCurrentTime()
            
            double currentProgress = progressBar.getProgress();
            double increment = 100.0 / (currentSong.duration.toMillis() / 100.0);
            
            double newProgress = currentProgress + (increment / 100.0);
            
            if (newProgress >= 1.0) {
                // Song finished, play next
                newProgress = 1.0;
                isPlaying = false;
                playNext();
            } else {
                progressBar.setProgress(newProgress);
                
                // Update current time display
                Duration currentTime = currentSong.duration.multiply(newProgress);
                currentTimeLabel.setText(formatTime(currentTime));
            }
        }
    }
    
    private void updatePlaylistDisplay() {
        playlistItems.clear();
        for (Song song : songList) {
            playlistItems.add(song.toString());
        }
    }
    
    private String formatTime(Duration duration) {
        if (duration == null) return "00:00";
        
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    // Public methods for testing or external control
    public void playSongAtIndex(int index) {
        loadSong(index);
    }
    
    public void togglePlayPause() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            timeline.play();
        } else {
            timeline.stop();
        }
    }
    
    public double getCurrentProgress() {
        return progressBar.getProgress();
    }
    
    public void setVolume(double volume) {
        volumeSlider.setValue(volume);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}
