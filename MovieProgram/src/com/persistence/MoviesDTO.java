package com.persistence;

public class MoviesDTO {
	private String MovTitle;
	private String MovDirector;
	private String MovActor;
	private String MovGenre;
	private String MovStory;
	private String MovOpenDate;
	private String MovView;
	private String MovRuntime;
	private String MovRating;
	private String MovTrailer;

	public MoviesDTO(String MovTitle) {
		super();
		this.MovTitle = MovTitle;
	}

	public MoviesDTO(String MovTitle, String MovDirector, String MovActor, String MovGenre, String MovStory, String MovOpenDate, String MovView, String MovRuntime, String MovRating, String MovTrailer) {
		this.MovTitle = MovTitle;
		this.MovDirector = MovDirector;
		this.MovActor = MovActor;
		this.MovGenre = MovGenre;
		this.MovStory = MovStory;
		this.MovOpenDate = MovOpenDate;
		this.MovView = MovView;
		this.MovRuntime = MovRuntime;
		this.MovRating = MovRating;
		this.MovTrailer = MovTrailer;
	}

	public String getMovTitle() {
		return MovTitle;
	}

	public void setMovTitle(String movTitle) {
		MovTitle = movTitle;
	}

	public String getMovDirector() {
		return MovDirector;
	}

	public void setMovDirector(String movDirector) {
		MovDirector = movDirector;
	}

	public String getMovActor() {
		return MovActor;
	}

	public void setMovActor(String movActor) {
		MovActor = movActor;
	}

	public String getMovGenre() {
		return MovGenre;
	}

	public void setMovGenre(String movGenre) {
		MovGenre = movGenre;
	}

	public String getMovStory() {
		return MovStory;
	}

	public void setMovStory(String movStory) {
		MovStory = movStory;
	}

	public String getMovOpenDate() {
		return MovOpenDate;
	}

	public void setMovOpenDate(String movOpenDate) {
		MovOpenDate = movOpenDate;
	}

	public String getMovView() {
		return MovView;
	}

	public void setMovView(String movView) {
		MovView = movView;
	}

	public String getMovRuntime() {
		return MovRuntime;
	}

	public void setMovRuntime(String movRuntime) {
		MovRuntime = movRuntime;
	}

	public String getMovRating() {
		return MovRating;
	}

	public void setMovRating(String movRating) {
		MovRating = movRating;
	}

	public String getMovTrailer() {
		return MovTrailer;
	}

	public void setMovTrailer(String movTrailer) {
		MovTrailer = movTrailer;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(MovTitle + " | ");
		sb.append(MovDirector + " | ");
		sb.append(MovActor + " | ");
		sb.append(MovGenre + " | ");
		sb.append(MovStory + " | ");
		sb.append(MovOpenDate + " | ");
		sb.append(MovView + " | ");
		sb.append(MovRuntime + " | ");
		sb.append(MovRating + " | ");
		sb.append(MovTrailer + " | ");
		return sb.toString();
	}
}
